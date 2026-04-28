/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ruoyi.common.exception.ServiceException
 *  com.ruoyi.common.utils.DateUtils
 *  com.ruoyi.common.utils.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 *  org.springframework.transaction.annotation.Transactional
 */
package com.ruoyi.system.service.experimental.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.experimental.AssetsCatalogTreeSelect;
import com.ruoyi.system.domain.experimental.AssetsDto;
import com.ruoyi.system.domain.experimental.AssetsUseDto;
import com.ruoyi.system.domain.experimental.ExpAssetsCatalog;
import com.ruoyi.system.mapper.experimental.AssetsMapper;
import com.ruoyi.system.service.experimental.AssetsService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssetsServiceImpl
implements AssetsService {
    private static final Logger log = LoggerFactory.getLogger(AssetsServiceImpl.class);
    @Autowired
    private AssetsMapper assetsMapper;

    @Override
    public List<ExpAssetsCatalog> queryCatalogList(ExpAssetsCatalog data) {
        return this.assetsMapper.queryCatalog(data);
    }

    @Override
    public List<AssetsCatalogTreeSelect> queryCatalog(ExpAssetsCatalog data) {
        List<ExpAssetsCatalog> calaList = this.assetsMapper.queryCatalog(data);
        return this.buildTreeSelect(calaList);
    }

    @Override
    public int insertCatalog(ExpAssetsCatalog data) {
        return this.assetsMapper.insertCatalog(data);
    }

    @Override
    @Transactional
    public int deleteCatalogById(Long catalogId) {
        return this.assetsMapper.deleteCatalogById(catalogId);
    }

    @Override
    @Transactional
    public int updateCatalog(ExpAssetsCatalog data) {
        return this.assetsMapper.updateCatalog(data);
    }

    @Override
    public ExpAssetsCatalog queryCatalogById(Long catalogId) {
        return this.assetsMapper.queryCatalogById(catalogId);
    }

    @Override
    public boolean checkExistAssets(Long catalogId) {
        int result = this.assetsMapper.checkExistAssets(catalogId);
        return result > 0;
    }

    public List<AssetsCatalogTreeSelect> buildTreeSelect(List<ExpAssetsCatalog> catalog) {
        List<ExpAssetsCatalog> catalogTrees = this.buildTree(catalog);
        return catalogTrees.stream().map(AssetsCatalogTreeSelect::new).collect(Collectors.toList());
    }

    public List<ExpAssetsCatalog> buildTree(List<ExpAssetsCatalog> catalog) {
        List<ExpAssetsCatalog> returnList = new ArrayList<ExpAssetsCatalog>();
        List tempList = catalog.stream().map(ExpAssetsCatalog::getAssetsCatalogId).collect(Collectors.toList());
        for (ExpAssetsCatalog cataitem : catalog) {
            if (tempList.contains(cataitem.getParentId())) continue;
            this.recursionFn(catalog, cataitem);
            returnList.add(cataitem);
        }
        if (returnList.isEmpty()) {
            returnList = catalog;
        }
        return returnList;
    }

    private void recursionFn(List<ExpAssetsCatalog> list, ExpAssetsCatalog t) {
        List<ExpAssetsCatalog> childList = this.getChildList(list, t);
        t.setChildren(childList);
        for (ExpAssetsCatalog tChild : childList) {
            if (!this.hasChild(list, tChild)) continue;
            this.recursionFn(list, tChild);
        }
    }

    private List<ExpAssetsCatalog> getChildList(List<ExpAssetsCatalog> list, ExpAssetsCatalog t) {
        ArrayList<ExpAssetsCatalog> tlist = new ArrayList<ExpAssetsCatalog>();
        for (ExpAssetsCatalog n : list) {
            if (!StringUtils.isNotNull((Object)n.getParentId()) || n.getParentId().longValue() != t.getAssetsCatalogId().longValue()) continue;
            tlist.add(n);
        }
        return tlist;
    }

    private boolean hasChild(List<ExpAssetsCatalog> list, ExpAssetsCatalog t) {
        return this.getChildList(list, t).size() > 0;
    }

    @Override
    public List<AssetsDto> selectAssetsList(AssetsDto assets) {
        return this.assetsMapper.selectAssetsList(assets);
    }

    @Override
    public AssetsDto selectExpAssetsByAssetsId(Long assetsId) {
        return this.assetsMapper.selectExpAssetsByAssetsId(assetsId);
    }

    @Override
    @Transactional
    public int insertAssets(AssetsDto assets) {
        assets.setCreateTime(DateUtils.getNowDate());
        return this.assetsMapper.insertAssets(assets);
    }

    @Override
    public int updateExpAssets(AssetsDto expAssets) {
        expAssets.setUpdateTime(DateUtils.getNowDate());
        return this.assetsMapper.updateExpAssets(expAssets);
    }

    @Override
    public int outAssets(AssetsUseDto assetsUse) {
        assetsUse.setCreateTime(DateUtils.getNowDate());
        this.assetsMapper.insertExpAssetsUse(assetsUse);
        Long total = assetsUse.getUseNumber() + assetsUse.getUseTotalNumber();
        assetsUse.setUseTotalNumber(total);
        return this.assetsMapper.outAssets(assetsUse);
    }

    @Override
    public int deleteExpAssetsByAssetsIds(Long[] assetsIds) {
        return this.assetsMapper.deleteExpAssetsByAssetsIds(assetsIds);
    }

    @Override
    public List<AssetsUseDto> selectExpAssetsUseList(AssetsUseDto expAssetsUse) {
        return this.assetsMapper.selectExpAssetsUseList(expAssetsUse);
    }

    @Override
    public String importAssets(List<AssetsDto> assetsList, Boolean isUpdateSupport) {
        if (StringUtils.isNull(assetsList) || assetsList.isEmpty()) {
            throw new ServiceException("\u5bfc\u5165\u8d44\u4ea7\u6570\u636e\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (AssetsDto assets : assetsList) {
            try {
                this.assetsMapper.insertAssets(assets);
                successMsg.append("<br/>").append(++successNum).append("\u3001\u5bfc\u5165\u6210\u529f");
            }
            catch (Exception e) {
                String msg = "<br/>" + ++failureNum + "\u3001\u5bfc\u5165\u5931\u8d25\uff1a";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, (Throwable)e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "\u5f88\u62b1\u6b49\uff0c\u5bfc\u5165\u5931\u8d25\uff01\u5171 " + failureNum + " \u6761\u6570\u636e\u683c\u5f0f\u4e0d\u6b63\u786e\uff0c\u9519\u8bef\u5982\u4e0b\uff1a");
            throw new ServiceException(failureMsg.toString());
        }
        successMsg.insert(0, "\u606d\u559c\u60a8\uff0c\u6570\u636e\u5df2\u5168\u90e8\u5bfc\u5165\u6210\u529f\uff01\u5171 " + successNum + " \u6761\uff0c\u6570\u636e\u5982\u4e0b\uff1a");
        return successMsg.toString();
    }
}

