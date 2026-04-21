package com.ruoyi.web.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.EleCatalog;
import com.ruoyi.system.service.IEleCatalogService;

/**
 * 元素目录信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/eleCatalog")
public class EleCatalogController extends BaseController
{
    @Autowired
    private IEleCatalogService catalogService;

    /**
     * 获取元素目录列表
     */
    @PreAuthorize("@ss.hasPermi('system:eleCatalog:list')")
    @GetMapping("/list")
    public AjaxResult list(EleCatalog eleCatalog)
    {
        List<EleCatalog> catalogs = catalogService.selectEleCatalogList(eleCatalog);
        return success(catalogs);
    }

    /**
     * 获取元素目录树结构
     */
    @PreAuthorize("@ss.hasPermi('system:eleCatalog:list')")
    @GetMapping("/tree")
    public AjaxResult tree(EleCatalog eleCatalog)
    {
        List<EleCatalog> catalogs = catalogService.selectEleCatalogList(eleCatalog);
        return success(catalogService.buildCatalogTree(catalogs));
    }

    /**
     * 根据目录编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:eleCatalog:query')")
    @GetMapping(value = "/{catalogId}")
    public AjaxResult getInfo(@PathVariable Long catalogId)
    {
        return success(catalogService.selectEleCatalogById(catalogId));
    }

    /**
     * 新增元素目录
     */
    @PreAuthorize("@ss.hasPermi('system:eleCatalog:add')")
    @Log(title = "元素目录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody EleCatalog eleCatalog)
    {
        if (!catalogService.checkCatalogCodeUnique(eleCatalog))
        {
            return error("新增目录'" + eleCatalog.getCatalogName() + "'失败，目录编码已存在");
        }
        eleCatalog.setCreateBy(getUsername());
        return toAjax(catalogService.insertEleCatalog(eleCatalog));
    }

    /**
     * 修改元素目录
     */
    @PreAuthorize("@ss.hasPermi('system:eleCatalog:edit')")
    @Log(title = "元素目录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody EleCatalog eleCatalog)
    {
        Long catalogId = eleCatalog.getCatalogId();
        if (!catalogService.checkCatalogCodeUnique(eleCatalog))
        {
            return error("修改目录'" + eleCatalog.getCatalogName() + "'失败，目录编码已存在");
        }
        else if (eleCatalog.getParentId().equals(catalogId))
        {
            return error("修改目录'" + eleCatalog.getCatalogName() + "'失败，上级目录不能是自己");
        }
        eleCatalog.setUpdateBy(getUsername());
        return toAjax(catalogService.updateEleCatalog(eleCatalog));
    }

    /**
     * 删除元素目录
     */
    @PreAuthorize("@ss.hasPermi('system:eleCatalog:remove')")
    @Log(title = "元素目录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{catalogId}")
    public AjaxResult remove(@PathVariable Long catalogId)
    {
        if (catalogService.hasChildByCatalogId(catalogId))
        {
            return warn("存在下级目录,不允许删除");
        }
        return toAjax(catalogService.deleteEleCatalogById(catalogId));
    }
}