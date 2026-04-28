/*
 * Decompiled with CFR 0.152.
 */
package com.ruoyi.system.service.experimental;

import com.ruoyi.system.domain.experimental.ExpOperateLog;
import java.util.List;

public interface ExpOperateLogService {
    public List<ExpOperateLog> selectExpOperateLogList(ExpOperateLog var1);

    public int recoverExp(List<ExpOperateLog> var1);

    public int deleteExpOperateLogById(Long logId);

    public int cleanExpOperateLog();
}

