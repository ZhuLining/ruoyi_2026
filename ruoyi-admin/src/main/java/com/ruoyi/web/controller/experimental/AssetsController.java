/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ruoyi.common.annotation.DataScope
 *  com.ruoyi.common.annotation.Log
 *  com.ruoyi.common.core.controller.BaseController
 *  com.ruoyi.common.core.domain.AjaxResult
 *  com.ruoyi.common.core.domain.entity.SysUser
 *  com.ruoyi.common.core.page.TableDataInfo
 *  com.ruoyi.common.enums.BusinessType
 *  com.ruoyi.common.utils.SecurityUtils
 *  com.ruoyi.common.utils.poi.ExcelUtil
 *  com.ruoyi.system.domain.experimental.AssetsDto
 *  com.ruoyi.system.domain.experimental.AssetsUseDto
 *  com.ruoyi.system.domain.experimental.ExpAssetsCatalog
 *  com.ruoyi.system.service.experimental.AssetsService
 *  com.ruoyi.web.controller.experimental.AssetsController
 *  jakarta.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.validation.annotation.Validated
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.ruoyi.web.controller.experimental;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.experimental.AssetsDto;
import com.ruoyi.system.domain.experimental.AssetsUseDto;
import com.ruoyi.system.domain.experimental.ExpAssetsCatalog;
import com.ruoyi.system.service.experimental.AssetsService;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * 资产目录信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping(value={"/experimental/assets"})
public class AssetsController
extends BaseController {
    @Autowired
    private AssetsService assetsService;

    @PreAuthorize(value="@ss.hasPermi('system:assetsCatalog:query')")
    @GetMapping(value={"/catalogList"})
    @DataScope(deptAlias="d", userAlias="u")
    public AjaxResult queryCatalogList(ExpAssetsCatalog data) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        data.setUserId(user.getUserId());
        data.setCreateBy(user.getUserName());
        data.setDeptId(user.getDeptId());
        List catalogList = this.assetsService.queryCatalogList(data);
        return this.success((Object)catalogList);
    }

    @PreAuthorize(value="@ss.hasPermi('system:assetsCatalog:query')")
    @GetMapping(value={"/catalogTree"})
    public AjaxResult queryCatalog(ExpAssetsCatalog data) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        data.setUserId(user.getUserId());
        return this.success((Object)this.assetsService.queryCatalog(data));
    }

    @PreAuthorize(value="@ss.hasPermi('system:assetsCatalog:add')")
    @Log(title="目录信息", businessType=BusinessType.INSERT)
    @PostMapping(value={"/catalogAdd"})
    public AjaxResult catalogAdd(@Validated @RequestBody ExpAssetsCatalog catalog) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        catalog.setUserId(user.getUserId());
        catalog.setCreateBy(user.getUserName());
        catalog.setDeptId(user.getDeptId());
        return this.toAjax(this.assetsService.insertCatalog(catalog));
    }

    @PreAuthorize(value="@ss.hasPermi('system:assetsCatalog:remove')")
    @Log(title="目录信息", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/delCatalog/{catalogId}"})
    public AjaxResult delCatalog(@PathVariable Long catalogId) {
        if (this.assetsService.checkExistAssets(catalogId)) {
            return this.warn("目录存在资产,不允许删除");
        }
        return this.toAjax(this.assetsService.deleteCatalogById(catalogId));
    }

    @PreAuthorize(value="@ss.hasPermi('system:assetsCatalog:edit')")
    @Log(title="目录信息", businessType=BusinessType.UPDATE)
    @PutMapping(value={"/updateCatalog"})
    public AjaxResult updateCatalog(@Validated @RequestBody ExpAssetsCatalog data) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        data.setUpdateBy(user.getUserName());
        return this.toAjax(this.assetsService.updateCatalog(data));
    }

    @PreAuthorize(value="@ss.hasPermi('system:assetsCatalog:query')")
    @GetMapping(value={"/queryCatalogById/{catalogId}"})
    public AjaxResult queryCatalogById(@PathVariable Long catalogId) {
        return this.success((Object)this.assetsService.queryCatalogById(catalogId));
    }

    @PreAuthorize(value="@ss.hasPermi('system:expAssets:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(AssetsDto assets) {
        this.startPage();
        List list = this.assetsService.selectAssetsList(assets);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('system:expAssets:list')")
    @GetMapping(value={"/allList"})
    public List<AssetsDto> getAll(AssetsDto assets) {
        return this.assetsService.selectAssetsList(assets);
    }

    @PreAuthorize(value="@ss.hasPermi('system:expAssets:query')")
    @GetMapping(value={"/{assetsId}"})
    public AjaxResult getInfo(@PathVariable(value="assetsId") Long assetsId) {
        return this.success((Object)this.assetsService.selectExpAssetsByAssetsId(assetsId));
    }

    @PreAuthorize(value="@ss.hasPermi('system:expAssets:add')")
    @Log(title="资产信息", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AssetsDto assets) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        assets.setUserId(user.getUserId());
        assets.setCreateBy(user.getUserName());
        assets.setDeptId(user.getDeptId());
        return this.toAjax(this.assetsService.insertAssets(assets));
    }

    @PreAuthorize(value="@ss.hasPermi('system:expAssets:edit')")
    @Log(title="资产信息", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AssetsDto expAssets) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        expAssets.setUpdateBy(user.getUserName());
        return this.toAjax(this.assetsService.updateExpAssets(expAssets));
    }

    @PreAuthorize(value="@ss.hasPermi('system:expAssets:remove')")
    @Log(title="资产信息", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{assetsIds}"})
    public AjaxResult remove(@PathVariable Long[] assetsIds) {
        return this.toAjax(this.assetsService.deleteExpAssetsByAssetsIds(assetsIds));
    }

    @PreAuthorize(value="@ss.hasPermi('system:expAssets:use')")
    @Log(title="资产信息", businessType=BusinessType.UPDATE)
    @PutMapping(value={"/out"})
    public AjaxResult updateCatalog(@Validated @RequestBody AssetsUseDto data) {
        return this.toAjax(this.assetsService.outAssets(data));
    }

    @PreAuthorize(value="@ss.hasPermi('system:expAssets:add')")
    @PostMapping(value={"/importTemplate"})
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil util = new ExcelUtil(AssetsDto.class);
        util.importTemplateExcel(response, "资产数据");
    }

    @Log(title="资产信息", businessType=BusinessType.IMPORT)
    @PreAuthorize(value="@ss.hasPermi('system:expAssets:add')")
    @PostMapping(value={"/importData"})
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil util = new ExcelUtil(AssetsDto.class);
        List assetsList = util.importExcel(file.getInputStream());
        String message = this.assetsService.importAssets(assetsList, Boolean.valueOf(updateSupport));
        return this.success(message);
    }

    @Log(title="资产信息", businessType=BusinessType.EXPORT)
    @PreAuthorize(value="@ss.hasPermi('system:expAssets:list')")
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, AssetsDto assets) {
        List list = this.assetsService.selectAssetsList(assets);
        ExcelUtil util = new ExcelUtil(AssetsDto.class);
        util.exportExcel(response, list, "资产数据");
    }

    @PreAuthorize(value="@ss.hasPermi('system:expAssets:list')")
    @GetMapping(value={"/useList"})
    public TableDataInfo list(AssetsUseDto expAssetsUse) {
        this.startPage();
        List list = this.assetsService.selectExpAssetsUseList(expAssetsUse);
        return this.getDataTable(list);
    }
}

