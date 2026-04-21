package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.EleTable;
import com.ruoyi.system.mapper.EleTableMapper;
import com.ruoyi.system.service.IEleTableService;

/**
 * 元素表 服务实现
 *
 * @author ruoyi
 */
@Service
public class EleTableServiceImpl implements IEleTableService
{
    @Autowired
    private EleTableMapper eleTableMapper;

    /**
     * 查询元素信息
     */
    @Override
    public EleTable selectEleTableById(Long eleId)
    {
        return eleTableMapper.selectEleTableById(eleId);
    }

    /**
     * 查询元素列表
     */
    @Override
    public List<EleTable> selectEleTableList(EleTable eleTable)
    {
        return eleTableMapper.selectEleTableList(eleTable);
    }

    /**
     * 新增元素
     */
    @Override
    public int insertEleTable(EleTable eleTable)
    {
        return eleTableMapper.insertEleTable(eleTable);
    }

    /**
     * 修改元素
     */
    @Override
    public int updateEleTable(EleTable eleTable)
    {
        return eleTableMapper.updateEleTable(eleTable);
    }

    /**
     * 批量删除元素
     */
    @Override
    public int deleteEleTableByIds(Long[] eleIds)
    {
        return eleTableMapper.deleteEleTableByIds(eleIds);
    }

    /**
     * 校验元素编码是否唯一
     */
    @Override
    public boolean checkEleCodeUnique(EleTable eleTable)
    {
        Long eleId = StringUtils.isNull(eleTable.getEleId()) ? -1L : eleTable.getEleId();
        EleTable info = eleTableMapper.checkEleCodeUnique(eleTable.getEleCode());
        if (StringUtils.isNotNull(info) && info.getEleId().longValue() != eleId.longValue())
        {
            return false;
        }
        return true;
    }
}
