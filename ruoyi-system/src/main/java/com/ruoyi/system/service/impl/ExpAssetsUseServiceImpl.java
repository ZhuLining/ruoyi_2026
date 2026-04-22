package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.ExpAssetsUse;
import com.ruoyi.system.mapper.ExpAssetsUseMapper;
import com.ruoyi.system.service.IExpAssetsUseService;

/**
 * 资产出库记录 服务实现
 *
 * @author ruoyi
 */
@Service
public class ExpAssetsUseServiceImpl implements IExpAssetsUseService
{
    @Autowired
    private ExpAssetsUseMapper expAssetsUseMapper;

    /**
     * 查询出库记录
     */
    @Override
    public ExpAssetsUse selectExpAssetsUseById(Long useId)
    {
        return expAssetsUseMapper.selectExpAssetsUseById(useId);
    }

    /**
     * 查询出库记录列表
     */
    @Override
    public List<ExpAssetsUse> selectExpAssetsUseList(ExpAssetsUse expAssetsUse)
    {
        return expAssetsUseMapper.selectExpAssetsUseList(expAssetsUse);
    }

    /**
     * 新增出库记录
     */
    @Override
    public int insertExpAssetsUse(ExpAssetsUse expAssetsUse)
    {
        return expAssetsUseMapper.insertExpAssetsUse(expAssetsUse);
    }

    /**
     * 修改出库记录
     */
    @Override
    public int updateExpAssetsUse(ExpAssetsUse expAssetsUse)
    {
        return expAssetsUseMapper.updateExpAssetsUse(expAssetsUse);
    }

    /**
     * 批量删除出库记录
     */
    @Override
    public int deleteExpAssetsUseByIds(Long[] useIds)
    {
        return expAssetsUseMapper.deleteExpAssetsUseByIds(useIds);
    }
}
