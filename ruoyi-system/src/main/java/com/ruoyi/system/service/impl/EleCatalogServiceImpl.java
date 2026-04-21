package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.EleCatalog;
import com.ruoyi.system.mapper.EleCatalogMapper;
import com.ruoyi.system.service.IEleCatalogService;

/**
 * 元素目录 服务实现
 *
 * @author ruoyi
 */
@Service
public class EleCatalogServiceImpl implements IEleCatalogService
{
    @Autowired
    private EleCatalogMapper catalogMapper;

    /**
     * 查询元素目录信息
     */
    @Override
    public EleCatalog selectEleCatalogById(Long catalogId)
    {
        return catalogMapper.selectEleCatalogById(catalogId);
    }

    /**
     * 查询元素目录列表
     */
    @Override
    public List<EleCatalog> selectEleCatalogList(EleCatalog eleCatalog)
    {
        return catalogMapper.selectEleCatalogList(eleCatalog);
    }

    /**
     * 构建前端所需要树结构
     */
    @Override
    public List<EleCatalog> buildCatalogTree(List<EleCatalog> catalogs)
    {
        List<EleCatalog> returnList = new ArrayList<EleCatalog>();
        List<Long> tempList = catalogs.stream().map(EleCatalog::getCatalogId).collect(Collectors.toList());
        for (EleCatalog catalog : catalogs)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(catalog.getParentId()))
            {
                recursionFn(catalogs, catalog);
                returnList.add(catalog);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = catalogs;
        }
        return returnList;
    }

    /**
     * 新增元素目录
     */
    @Override
    public int insertEleCatalog(EleCatalog eleCatalog)
    {
        EleCatalog info = catalogMapper.selectEleCatalogById(eleCatalog.getParentId());
        if (StringUtils.isNotNull(info))
        {
            eleCatalog.setAncestors(info.getAncestors() + "," + eleCatalog.getParentId());
        }
        else
        {
            eleCatalog.setAncestors("0");
        }
        return catalogMapper.insertEleCatalog(eleCatalog);
    }

    /**
     * 修改元素目录
     */
    @Override
    public int updateEleCatalog(EleCatalog eleCatalog)
    {
        EleCatalog newParentCatalog = catalogMapper.selectEleCatalogById(eleCatalog.getParentId());
        EleCatalog oldCatalog = catalogMapper.selectEleCatalogById(eleCatalog.getCatalogId());
        if (StringUtils.isNotNull(newParentCatalog) && StringUtils.isNotNull(oldCatalog))
        {
            String newAncestors = newParentCatalog.getAncestors() + "," + newParentCatalog.getCatalogId();
            String oldAncestors = oldCatalog.getAncestors();
            eleCatalog.setAncestors(newAncestors);
            updateCatalogChildren(eleCatalog.getCatalogId(), newAncestors, oldAncestors);
        }
        return catalogMapper.updateEleCatalog(eleCatalog);
    }

    /**
     * 修改子元素关系
     */
    public void updateCatalogChildren(Long catalogId, String newAncestors, String oldAncestors)
    {
        List<EleCatalog> children = catalogMapper.selectEleCatalogList(
            new EleCatalog() {{ setParentId(catalogId); }}
        );
        for (EleCatalog child : children)
        {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            for (EleCatalog child : children)
            {
                catalogMapper.updateEleCatalog(child);
            }
        }
    }

    /**
     * 删除元素目录信息
     */
    @Override
    public int deleteEleCatalogById(Long catalogId)
    {
        return catalogMapper.deleteEleCatalogById(catalogId);
    }

    /**
     * 批量删除元素目录
     */
    @Override
    public int deleteEleCatalogByIds(Long[] catalogIds)
    {
        return catalogMapper.deleteEleCatalogByIds(catalogIds);
    }

    /**
     * 校验目录编码是否唯一
     */
    @Override
    public boolean checkCatalogCodeUnique(EleCatalog eleCatalog)
    {
        Long catalogId = StringUtils.isNull(eleCatalog.getCatalogId()) ? -1L : eleCatalog.getCatalogId();
        EleCatalog info = catalogMapper.checkCatalogCodeUnique(eleCatalog.getCatalogCode());
        if (StringUtils.isNotNull(info) && info.getCatalogId().longValue() != catalogId.longValue())
        {
            return false;
        }
        return true;
    }

    /**
     * 是否存在子节点
     */
    @Override
    public boolean hasChildByCatalogId(Long catalogId)
    {
        int result = catalogMapper.hasChildByCatalogId(catalogId);
        return result > 0;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<EleCatalog> list, EleCatalog t)
    {
        List<EleCatalog> childList = getChildList(list, t);
        t.setChildren(childList);
        for (EleCatalog tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<EleCatalog> getChildList(List<EleCatalog> list, EleCatalog t)
    {
        List<EleCatalog> tlist = new ArrayList<EleCatalog>();
        Iterator<EleCatalog> it = list.iterator();
        while (it.hasNext())
        {
            EleCatalog n = it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getCatalogId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<EleCatalog> list, EleCatalog t)
    {
        return getChildList(list, t).size() > 0;
    }
}
