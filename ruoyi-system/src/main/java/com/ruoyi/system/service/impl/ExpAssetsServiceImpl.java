package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.ExpAssets;
import com.ruoyi.system.mapper.ExpAssetsMapper;
import com.ruoyi.system.service.IExpAssetsService;

/**
 * 资产信息 服务实现
 *
 * @author ruoyi
 */
@Service
public class ExpAssetsServiceImpl implements IExpAssetsService
{
    @Autowired
    private ExpAssetsMapper expAssetsMapper;

    /**
     * 查询资产信息
     */
    @Override
    public ExpAssets selectExpAssetsById(Long assetsId)
    {
        return expAssetsMapper.selectExpAssetsById(assetsId);
    }

    /**
     * 查询资产列表
     */
    @Override
    public List<ExpAssets> selectExpAssetsList(ExpAssets expAssets)
    {
        return expAssetsMapper.selectExpAssetsList(expAssets);
    }

    /**
     * 新增资产
     */
    @Override
    public int insertExpAssets(ExpAssets expAssets)
    {
        return expAssetsMapper.insertExpAssets(expAssets);
    }

    /**
     * 修改资产
     */
    @Override
    public int updateExpAssets(ExpAssets expAssets)
    {
        return expAssetsMapper.updateExpAssets(expAssets);
    }

    /**
     * 批量删除资产
     */
    @Override
    public int deleteExpAssetsByIds(Long[] assetsIds)
    {
        return expAssetsMapper.deleteExpAssetsByIds(assetsIds);
    }
}
