package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ExpAssetsUse;

/**
 * 资产出库记录 服务层
 *
 * @author ruoyi
 */
public interface IExpAssetsUseService
{
    /**
     * 查询出库记录
     *
     * @param useId 出库ID
     * @return 出库记录
     */
    public ExpAssetsUse selectExpAssetsUseById(Long useId);

    /**
     * 查询出库记录列表
     *
     * @param expAssetsUse 出库记录
     * @return 出库记录集合
     */
    public List<ExpAssetsUse> selectExpAssetsUseList(ExpAssetsUse expAssetsUse);

    /**
     * 新增出库记录
     *
     * @param expAssetsUse 出库记录
     * @return 结果
     */
    public int insertExpAssetsUse(ExpAssetsUse expAssetsUse);

    /**
     * 修改出库记录
     *
     * @param expAssetsUse 出库记录
     * @return 结果
     */
    public int updateExpAssetsUse(ExpAssetsUse expAssetsUse);

    /**
     * 批量删除出库记录
     *
     * @param useIds 需要删除的出库ID
     * @return 结果
     */
    public int deleteExpAssetsUseByIds(Long[] useIds);
}
