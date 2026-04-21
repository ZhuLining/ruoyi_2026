package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.EleTable;

/**
 * 元素表 服务层
 *
 * @author ruoyi
 */
public interface IEleTableService
{
    /**
     * 查询元素信息
     *
     * @param eleId 元素ID
     * @return 元素信息
     */
    public EleTable selectEleTableById(Long eleId);

    /**
     * 查询元素列表
     *
     * @param eleTable 元素信息
     * @return 元素集合
     */
    public List<EleTable> selectEleTableList(EleTable eleTable);

    /**
     * 新增元素
     *
     * @param eleTable 元素信息
     * @return 结果
     */
    public int insertEleTable(EleTable eleTable);

    /**
     * 修改元素
     *
     * @param eleTable 元素信息
     * @return 结果
     */
    public int updateEleTable(EleTable eleTable);

    /**
     * 批量删除元素
     *
     * @param eleIds 需要删除的元素ID
     * @return 结果
     */
    public int deleteEleTableByIds(Long[] eleIds);

    /**
     * 校验元素编码是否唯一
     *
     * @param eleTable 元素信息
     * @return 结果
     */
    public boolean checkEleCodeUnique(EleTable eleTable);
}
