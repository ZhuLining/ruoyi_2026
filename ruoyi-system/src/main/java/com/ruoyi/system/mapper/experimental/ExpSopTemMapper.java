/*
 * Decompiled with CFR 0.152.
 */
package com.ruoyi.system.mapper.experimental;

import com.ruoyi.system.domain.experimental.ExpAssetsDto;
import com.ruoyi.system.domain.experimental.ExpStep;
import com.ruoyi.system.domain.experimental.ExpTemBaseDto;
import com.ruoyi.system.domain.experimental.ExpTemCatalogDto;
import com.ruoyi.system.domain.experimental.ExpTemDto;
import java.util.List;

public interface ExpSopTemMapper {
    public List<ExpTemCatalogDto> queryCatalog(ExpTemCatalogDto var1);

    public int insertCatalog(ExpTemCatalogDto var1);

    public int deleteCatalogById(Long var1);

    public int updateCatalog(ExpTemCatalogDto var1);

    public ExpTemCatalogDto queryCatalogById(Long var1);

    public int insertTem(ExpTemBaseDto var1);

    public int insertTemStep(ExpTemBaseDto var1);

    public int insertExpEle(ExpTemBaseDto var1);

    public int insertExpEleData(ExpTemBaseDto var1);

    public int insertExpAssets(ExpAssetsDto var1);

    public ExpTemBaseDto queryTemById(Long var1);

    public List<ExpStep> queryStepById(Long var1);

    public List<ExpTemBaseDto> selectExpById(Long var1);

    public List<ExpAssetsDto> selectExpAssById(Long var1);

    public int updateTem(ExpTemBaseDto var1);

    public int deleteExpEle(Long[] var1);

    public int deleteExpEleData(Long[] var1);

    public int deleteExpStep(Long[] var1);

    public int deleteExpAssets(Long[] var1);

    public int deleteExpTableByTemIds(Long[] var1);

    public List<ExpTemDto> queryList(ExpTemDto var1);

    public int checkExistTem(Long var1);
}

