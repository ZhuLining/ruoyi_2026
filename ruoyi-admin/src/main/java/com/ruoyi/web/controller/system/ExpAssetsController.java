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
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.ExpAssets;
import com.ruoyi.system.domain.ExpAssetsUse;
import com.ruoyi.system.service.IExpAssetsService;
import com.ruoyi.system.service.IExpAssetsUseService;

/**
 * 资产信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/expAssets")
public class ExpAssetsController extends BaseController
{
    @Autowired
    private IExpAssetsService expAssetsService;

    @Autowired
    private IExpAssetsUseService expAssetsUseService;

    /**
     * 查询资产列表
     */
    @PreAuthorize("@ss.hasPermi('system:expAssets:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExpAssets expAssets)
    {
        startPage();
        List<ExpAssets> list = expAssetsService.selectExpAssetsList(expAssets);
        return getDataTable(list);
    }

    /**
     * 根据资产编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:expAssets:query')")
    @GetMapping(value = "/{assetsId}")
    public AjaxResult getInfo(@PathVariable Long assetsId)
    {
        return success(expAssetsService.selectExpAssetsById(assetsId));
    }

    /**
     * 新增资产（入库）
     */
    @PreAuthorize("@ss.hasPermi('system:expAssets:add')")
    @Log(title = "资产管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ExpAssets expAssets)
    {
        expAssets.setCreateBy(getUsername());
        expAssets.setUserId(SecurityUtils.getUserId());
        expAssets.setDeptId(SecurityUtils.getDeptId());
        expAssets.setUseTotalNumber(0L);
        return toAjax(expAssetsService.insertExpAssets(expAssets));
    }

    /**
     * 修改资产
     */
    @PreAuthorize("@ss.hasPermi('system:expAssets:edit')")
    @Log(title = "资产管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ExpAssets expAssets)
    {
        expAssets.setUpdateBy(getUsername());
        return toAjax(expAssetsService.updateExpAssets(expAssets));
    }

    /**
     * 删除资产
     */
    @PreAuthorize("@ss.hasPermi('system:expAssets:remove')")
    @Log(title = "资产管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{assetsIds}")
    public AjaxResult remove(@PathVariable Long[] assetsIds)
    {
        return toAjax(expAssetsService.deleteExpAssetsByIds(assetsIds));
    }

    /**
     * 资产出库
     */
    @PreAuthorize("@ss.hasPermi('system:expAssets:use')")
    @Log(title = "资产管理", businessType = BusinessType.UPDATE)
    @PostMapping("/use")
    public AjaxResult use(@RequestBody ExpAssetsUse expAssetsUse)
    {
        ExpAssets assets = expAssetsService.selectExpAssetsById(expAssetsUse.getAssetsId());
        if (assets == null)
        {
            return error("资产不存在");
        }
        long remain = assets.getAssetsNumber() - assets.getUseTotalNumber();
        if (expAssetsUse.getUseNumber() > remain)
        {
            return error("出库数量不能大于剩余库存");
        }

        // 更新资产出库数量
        assets.setUseTotalNumber(assets.getUseTotalNumber() + expAssetsUse.getUseNumber());
        expAssetsService.updateExpAssets(assets);

        // 插入出库记录
        expAssetsUse.setUserId(SecurityUtils.getUserId());
        expAssetsUse.setUserName(getUsername());
        expAssetsUse.setCreateBy(getUsername());
        expAssetsUseService.insertExpAssetsUse(expAssetsUse);

        return success();
    }
}
