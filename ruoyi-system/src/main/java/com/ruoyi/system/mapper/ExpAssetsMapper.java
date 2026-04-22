package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ExpAssets;

/**
 * 资产信息Mapper接口
 *
 * @author ruoyi
 */
public interface ExpAssetsMapper
{
    /**
     * 查询资产信息
     *
     * @param assetsId 资产ID
     * @return 资产信息
     */
    public ExpAssets selectExpAssetsById(Long assetsId);

    /**
     * 查询资产列表
     *
     * @param expAssets 资产信息
     * @return 资产集合
     */
    public List<ExpAssets> selectExpAssetsList(ExpAssets expAssets);

    /**
     * 新增资产
     *
     * @param expAssets 资产信息
     * @return 结果
     */
    public int insertExpAssets(ExpAssets expAssets);

    /**
     * 修改资产
     *
     * @param expAssets 资产信息
     * @return 结果
     */
    public int updateExpAssets(ExpAssets expAssets);

    /**
     * 删除资产
     *
     * @param assetsId 资产ID
     * @return 结果
     */
    public int deleteExpAssetsById(Long assetsId);

    /**
     * 批量删除资产
     *
     * @param assetsIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExpAssetsByIds(Long[] assetsIds);
}
