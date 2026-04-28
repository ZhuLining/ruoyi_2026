/*
 * Decompiled with CFR 0.152.
 */
package com.ruoyi.system.service.experimental;

import com.ruoyi.system.domain.experimental.ExpTemBaseDto;
import com.ruoyi.system.domain.experimental.ExpTemDto;
import java.util.List;

public interface ExpTemService {
    public List<ExpTemDto> queryList(ExpTemDto var1);

    public int insertTem(ExpTemBaseDto var1);

    public int updateTem(ExpTemBaseDto var1);

    public ExpTemBaseDto queryTemById(Long var1);

    public List<ExpTemBaseDto> selectTemById(Long var1);

    public int deleteExpTableByTemIds(Long[] var1);
}

