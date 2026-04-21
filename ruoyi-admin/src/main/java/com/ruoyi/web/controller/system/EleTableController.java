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
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.EleTable;
import com.ruoyi.system.service.IEleTableService;

/**
 * 元素信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/eleTable")
public class EleTableController extends BaseController
{
    @Autowired
    private IEleTableService eleTableService;

    /**
     * 查询元素列表
     */
    @PreAuthorize("@ss.hasPermi('system:eleTable:list')")
    @GetMapping("/list")
    public TableDataInfo list(EleTable eleTable)
    {
        startPage();
        List<EleTable> list = eleTableService.selectEleTableList(eleTable);
        return getDataTable(list);
    }

    /**
     * 根据元素编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:eleTable:query')")
    @GetMapping(value = "/{eleId}")
    public AjaxResult getInfo(@PathVariable Long eleId)
    {
        return success(eleTableService.selectEleTableById(eleId));
    }

    /**
     * 新增元素
     */
    @PreAuthorize("@ss.hasPermi('system:eleTable:add')")
    @Log(title = "元素管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody EleTable eleTable)
    {
        if (!eleTableService.checkEleCodeUnique(eleTable))
        {
            return error("新增元素'" + eleTable.getEleName() + "'失败，元素编码已存在");
        }
        eleTable.setCreateBy(getUsername());
        return toAjax(eleTableService.insertEleTable(eleTable));
    }

    /**
     * 修改元素
     */
    @PreAuthorize("@ss.hasPermi('system:eleTable:edit')")
    @Log(title = "元素管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody EleTable eleTable)
    {
        if (!eleTableService.checkEleCodeUnique(eleTable))
        {
            return error("修改元素'" + eleTable.getEleName() + "'失败，元素编码已存在");
        }
        eleTable.setUpdateBy(getUsername());
        return toAjax(eleTableService.updateEleTable(eleTable));
    }

    /**
     * 删除元素
     */
    @PreAuthorize("@ss.hasPermi('system:eleTable:remove')")
    @Log(title = "元素管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{eleIds}")
    public AjaxResult remove(@PathVariable Long[] eleIds)
    {
        return toAjax(eleTableService.deleteEleTableByIds(eleIds));
    }
}