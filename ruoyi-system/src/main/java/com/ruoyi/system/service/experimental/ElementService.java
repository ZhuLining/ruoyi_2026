/*
 * Decompiled with CFR 0.152.
 */
package com.ruoyi.system.service.experimental;

import com.ruoyi.system.domain.experimental.ExpElementDto;
import java.util.List;

public interface ElementService {
    public List<ExpElementDto> queryList(ExpElementDto var1);

    public int insertData(ExpElementDto var1);

    public int updateElem(ExpElementDto var1);

    public int deleteElemByIds(Long[] var1);
}

