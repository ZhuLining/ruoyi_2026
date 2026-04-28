/*
 * Decompiled with CFR 0.152.
 */
package com.ruoyi.system.service.experimental;

import com.ruoyi.system.domain.experimental.ExpBaseDto;
import com.ruoyi.system.domain.experimental.ExpStep;
import com.ruoyi.system.domain.experimental.ExperimentDto;
import com.ruoyi.system.domain.SysFileInfo;
import java.util.List;

public interface ExperimentService {
    public List<ExperimentDto> queryList(ExperimentDto var1);

    public List<ExperimentDto> queryUnfinishList(ExperimentDto var1);

    public List<ExperimentDto> queryFinishList(ExperimentDto var1);

    public List<ExpBaseDto> queryExpList(ExpBaseDto var1);

    public int insertExp(ExpBaseDto var1);

    public int updateExp(ExpBaseDto var1);

    public List<ExpBaseDto> selectExpById(Long var1);

    public ExpBaseDto queryExpById(Long var1);

    public int checkExp(ExperimentDto var1);

    public int insertResult(ExpBaseDto var1);

    public ExpBaseDto queryDetailById(Long var1);

    public List<ExpBaseDto> selectDetailById(Long var1);

    public int deleteExpTableByExpIds(List<ExpBaseDto> var1);

    public List<SysFileInfo> selectSysFileInfoList(Long var1);

    public List<List<ExpStep>> selectDetail(ExpBaseDto var1);
}

