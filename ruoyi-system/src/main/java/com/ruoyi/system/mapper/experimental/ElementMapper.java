/*
 * Decompiled with CFR 0.152.
 */
package com.ruoyi.system.mapper.experimental;

import com.ruoyi.system.domain.experimental.ExpElementDto;
import java.util.List;

public interface ElementMapper {
    public List<ExpElementDto> queryList(ExpElementDto var1);

    public int insertData(ExpElementDto var1);

    public int updateElem(ExpElementDto var1);

    public int deleteElemByIds(Long[] var1);
}

