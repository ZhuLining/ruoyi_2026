package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.EleTable;

/**
 * 元素表Mapper接口
 *
 * @author ruoyi
 */
public interface EleTableMapper
{
    /**
     * 查询元素
     *
     * @param eleId 元素ID
     * @return 元素
     */
    public EleTable selectEleTableById(Long eleId);

    /**
     * 查询元素列表
     *
     * @param eleTable 元素
     * @return 元素集合
     */
    public List<EleTable> selectEleTableList(EleTable eleTable);

    /**
     * 新增元素
     *
     * @param eleTable 元素
     * @return 结果
     */
    public int insertEleTable(EleTable eleTable);

    /**
     * 修改元素
     *
     * @param eleTable 元素
     * @return 结果
     */
    public int updateEleTable(EleTable eleTable);

    /**
     * 删除元素
     *
     * @param eleId 元素ID
     * @return 结果
     */
    public int deleteEleTableById(Long eleId);

    /**
     * 批量删除元素
     *
     * @param eleIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteEleTableByIds(Long[] eleIds);

    /**
     * 校验元素编码是否唯一
     *
     * @param eleCode 元素编码
     * @return 结果
     */
    public EleTable checkEleCodeUnique(String eleCode);
}
