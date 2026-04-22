package com.ruoyi.web.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.ExpAssetsUse;
import com.ruoyi.system.service.IExpAssetsUseService;

/**
 * 资产出库记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/expAssetsUse")
public class ExpAssetsUseController extends BaseController
{
    @Autowired
    private IExpAssetsUseService expAssetsUseService;

    /**
     * 查询出库记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:expAssets:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExpAssetsUse expAssetsUse)
    {
        startPage();
        List<ExpAssetsUse> list = expAssetsUseService.selectExpAssetsUseList(expAssetsUse);
        return getDataTable(list);
    }

    /**
     * 根据出库编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:expAssets:query')")
    @GetMapping(value = "/{useId}")
    public AjaxResult getInfo(@PathVariable Long useId)
    {
        return success(expAssetsUseService.selectExpAssetsUseById(useId));
    }

    /**
     * 删除出库记录
     */
    @PreAuthorize("@ss.hasPermi('system:expAssets:remove')")
    @Log(title = "资产出库记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{useIds}")
    public AjaxResult remove(@PathVariable Long[] useIds)
    {
        return toAjax(expAssetsUseService.deleteExpAssetsUseByIds(useIds));
    }
}
