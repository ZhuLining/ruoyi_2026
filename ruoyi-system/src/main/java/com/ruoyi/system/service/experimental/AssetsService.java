/*
 * Decompiled with CFR 0.152.
 */
package com.ruoyi.system.service.experimental;

import com.ruoyi.system.domain.experimental.AssetsCatalogTreeSelect;
import com.ruoyi.system.domain.experimental.AssetsDto;
import com.ruoyi.system.domain.experimental.AssetsUseDto;
import com.ruoyi.system.domain.experimental.ExpAssetsCatalog;
import java.util.List;

public interface AssetsService {
    public List<ExpAssetsCatalog> queryCatalogList(ExpAssetsCatalog var1);

    public List<AssetsCatalogTreeSelect> queryCatalog(ExpAssetsCatalog var1);

    public int insertCatalog(ExpAssetsCatalog var1);

    public int deleteCatalogById(Long var1);

    public int updateCatalog(ExpAssetsCatalog var1);

    public ExpAssetsCatalog queryCatalogById(Long var1);

    public boolean checkExistAssets(Long var1);

    public List<AssetsDto> selectAssetsList(AssetsDto var1);

    public AssetsDto selectExpAssetsByAssetsId(Long var1);

    public int insertAssets(AssetsDto var1);

    public int updateExpAssets(AssetsDto var1);

    public int outAssets(AssetsUseDto var1);

    public int deleteExpAssetsByAssetsIds(Long[] var1);

    public List<AssetsUseDto> selectExpAssetsUseList(AssetsUseDto var1);

    public String importAssets(List<AssetsDto> var1, Boolean var2);
}

