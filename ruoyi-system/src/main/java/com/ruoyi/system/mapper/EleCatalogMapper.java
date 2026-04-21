package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.EleCatalog;

/**
 * 元素目录Mapper接口
 *
 * @author ruoyi
 */
public interface EleCatalogMapper
{
    /**
     * 查询元素目录
     *
     * @param catalogId 目录ID
     * @return 元素目录
     */
    public EleCatalog selectEleCatalogById(Long catalogId);

    /**
     * 查询元素目录列表
     *
     * @param eleCatalog 元素目录
     * @return 元素目录集合
     */
    public List<EleCatalog> selectEleCatalogList(EleCatalog eleCatalog);

    /**
     * 新增元素目录
     *
     * @param eleCatalog 元素目录
     * @return 结果
     */
    public int insertEleCatalog(EleCatalog eleCatalog);

    /**
     * 修改元素目录
     *
     * @param eleCatalog 元素目录
     * @return 结果
     */
    public int updateEleCatalog(EleCatalog eleCatalog);

    /**
     * 删除元素目录
     *
     * @param catalogId 目录ID
     * @return 结果
     */
    public int deleteEleCatalogById(Long catalogId);

    /**
     * 批量删除元素目录
     *
     * @param catalogIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteEleCatalogByIds(Long[] catalogIds);

    /**
     * 校验目录编码是否唯一
     *
     * @param catalogCode 目录编码
     * @return 结果
     */
    public EleCatalog checkCatalogCodeUnique(String catalogCode);

    /**
     * 是否存在子节点
     *
     * @param catalogId 目录ID
     * @return 结果
     */
    public int hasChildByCatalogId(Long catalogId);
}
