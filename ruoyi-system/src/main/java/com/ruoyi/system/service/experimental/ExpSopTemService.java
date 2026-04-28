/*
 * Decompiled with CFR 0.152.
 */
package com.ruoyi.system.service.experimental;

import com.ruoyi.system.domain.experimental.ExpTemBaseDto;
import com.ruoyi.system.domain.experimental.ExpTemCatalogDto;
import com.ruoyi.system.domain.experimental.ExpTemDto;
import com.ruoyi.system.domain.experimental.TemCatalogTreeSelect;
import java.util.List;

public interface ExpSopTemService {
    public List<ExpTemCatalogDto> queryCatalogList(ExpTemCatalogDto var1);

    public List<TemCatalogTreeSelect> queryCatalog(ExpTemCatalogDto var1);

    public List<TemCatalogTreeSelect> buildTreeSelect(List<ExpTemCatalogDto> var1);

    public List<ExpTemCatalogDto> buildTree(List<ExpTemCatalogDto> var1);

    public int insertCatalog(ExpTemCatalogDto var1);

    public int deleteCatalogById(Long var1);

    public int updateCatalog(ExpTemCatalogDto var1);

    public ExpTemCatalogDto queryCatalogById(Long var1);

    public List<ExpTemDto> queryList(ExpTemDto var1);

    public int insertTem(ExpTemBaseDto var1);

    public int updateTem(ExpTemBaseDto var1);

    public ExpTemBaseDto queryTemById(Long var1);

    public List<ExpTemBaseDto> selectTemById(Long var1);

    public int deleteExpTableByTemIds(Long[] var1);

    public boolean checkExistTem(Long var1);
}

