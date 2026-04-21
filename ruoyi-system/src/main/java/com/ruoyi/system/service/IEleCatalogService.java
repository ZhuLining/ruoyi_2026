package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.EleCatalog;

/**
 * 元素目录 服务层
 *
 * @author ruoyi
 */
public interface IEleCatalogService
{
    /**
     * 查询元素目录信息
     *
     * @param catalogId 目录ID
     * @return 目录信息
     */
    public EleCatalog selectEleCatalogById(Long catalogId);

    /**
     * 查询元素目录列表
     *
     * @param eleCatalog 目录信息
     * @return 目录集合
     */
    public List<EleCatalog> selectEleCatalogList(EleCatalog eleCatalog);

    /**
     * 构建前端所需要树结构
     *
     * @param catalogs 目录列表
     * @return 树结构列表
     */
    public List<EleCatalog> buildCatalogTree(List<EleCatalog> catalogs);

    /**
     * 新增元素目录
     *
     * @param eleCatalog 目录信息
     * @return 结果
     */
    public int insertEleCatalog(EleCatalog eleCatalog);

    /**
     * 修改元素目录
     *
     * @param eleCatalog 目录信息
     * @return 结果
     */
    public int updateEleCatalog(EleCatalog eleCatalog);

    /**
     * 删除元素目录信息
     *
     * @param catalogId 目录ID
     * @return 结果
     */
    public int deleteEleCatalogById(Long catalogId);

    /**
     * 批量删除元素目录
     *
     * @param catalogIds 需要删除的目录ID
     * @return 结果
     */
    public int deleteEleCatalogByIds(Long[] catalogIds);

    /**
     * 校验目录编码是否唯一
     *
     * @param eleCatalog 目录信息
     * @return 结果
     */
    public boolean checkCatalogCodeUnique(EleCatalog eleCatalog);

    /**
     * 是否存在子节点
     *
     * @param catalogId 目录ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean hasChildByCatalogId(Long catalogId);
}
