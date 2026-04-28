/*
 * Decompiled with CFR 0.152.
 */
package com.ruoyi.system.mapper.experimental;

import com.ruoyi.system.domain.experimental.ExpAssetsDto;
import com.ruoyi.system.domain.experimental.ExpBaseDto;
import com.ruoyi.system.domain.experimental.ExpStep;
import com.ruoyi.system.domain.experimental.ExperimentDto;
import com.ruoyi.system.domain.SysFileInfo;
import java.util.List;

public interface ExperimentMapper {
    public List<ExperimentDto> queryList(ExperimentDto var1);

    public List<ExperimentDto> queryUnfinishList(ExperimentDto var1);

    public List<ExperimentDto> queryFinishList(ExperimentDto var1);

    public List<ExpBaseDto> queryExpList(ExpBaseDto var1);

    public int insertExp(ExpBaseDto var1);

    public int updateExp(ExpBaseDto var1);

    public int insertExpEle(ExpBaseDto var1);

    public int insertExpStep(ExpBaseDto var1);

    public int insertExpAssets(ExpAssetsDto var1);

    public int insertExpEleData(ExpBaseDto var1);

    public List<ExpBaseDto> selectExpById(Long var1);

    public ExpBaseDto queryExpById(Long var1);

    public List<ExpStep> queryStepById(Long var1);

    public List<ExpAssetsDto> selectExpAssById(Long var1);

    public int checkExp(ExperimentDto var1);

    public int updateExpFinish(ExpBaseDto var1);

    public int deleteExpTaskFinish(Long var1);

    public int updateExpStep(ExpBaseDto var1);

    public int insertExpTaskFinish(ExpBaseDto var1);

    public ExpBaseDto queryDetailById(Long var1);

    public List<ExpStep> queryStepDetailById(Long var1);

    public int deleteExpStep(List<Long> var1);

    public int deleteExpEle(List<Long> var1);

    public int deleteExpEleData(List<Long> var1);

    public int deleteExpAssets(List<Long> var1);

    public int deleteExpTableByExpIds(List<Long> var1);

    public int logicalDeleteExpTable(List<Long> var1);

    public int logicalRecoverExpTable(List<Long> var1);

    public List<ExpStep> queryStepList(Long[] var1);

    public List<SysFileInfo> selectSysFileInfoList(Long var1);

    public int batchSysFileInfo(List<SysFileInfo> var1);
}

