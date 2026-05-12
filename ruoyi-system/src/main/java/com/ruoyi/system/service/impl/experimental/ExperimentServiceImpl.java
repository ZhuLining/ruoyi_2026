/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ruoyi.common.annotation.DataScope
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.ruoyi.system.service.experimental.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.system.domain.SysFileInfo;
import com.ruoyi.system.domain.experimental.ExpAssetsDto;
import com.ruoyi.system.domain.experimental.ExpBaseDto;
import com.ruoyi.system.domain.experimental.ExpStep;
import com.ruoyi.system.domain.experimental.ExperimentDto;
import com.ruoyi.system.mapper.experimental.ExpOperateLogMapper;
import com.ruoyi.system.mapper.experimental.ExperimentMapper;
import com.ruoyi.system.service.experimental.ExperimentService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExperimentServiceImpl
implements ExperimentService {
    private static final Logger log = LoggerFactory.getLogger(ExperimentServiceImpl.class);
    @Autowired
    private ExperimentMapper experimentMapper;
    @Autowired
    private ExpOperateLogMapper expOperateLogMapper;

    @Override
    @DataScope(deptAlias="d", userAlias="u")
    public List<ExperimentDto> queryList(ExperimentDto exp) {
        return this.experimentMapper.queryList(exp);
    }

    @Override
    @DataScope(deptAlias="d", userAlias="u")
    public List<ExperimentDto> queryUnfinishList(ExperimentDto exp) {
        return this.experimentMapper.queryUnfinishList(exp);
    }

    @Override
    @DataScope(deptAlias="d", userAlias="u")
    public List<ExperimentDto> queryFinishList(ExperimentDto exp) {
        return this.experimentMapper.queryFinishList(exp);
    }

    @Override
    public List<ExpBaseDto> queryExpList(ExpBaseDto exp) {
        return this.experimentMapper.queryExpList(exp);
    }

    @Override
    @Transactional
    public int insertExp(ExpBaseDto exp) {
        int update = this.experimentMapper.insertExp(exp);
        Long expId = exp.getExpId();
        log.info("expId: {}", (Object)expId);
        List<ExpBaseDto> stepList = exp.getStepList();
        stepList.forEach(stepItem -> {
            stepItem.setExpId(expId);
            this.experimentMapper.insertExpStep((ExpBaseDto)((Object)stepItem));
            Long stepId = stepItem.getStepId();
            List<ExpBaseDto> eleList = stepItem.getEleList();
            eleList.forEach(eleItem -> {
                eleItem.setExpId(expId);
                eleItem.setStepId(stepId);
                this.experimentMapper.insertExpEle((ExpBaseDto)((Object)((Object)eleItem)));
                Long expEleId = eleItem.getExpEleId();
                List<ExpBaseDto> eleContent = eleItem.getEleContent();
                eleContent.forEach(conItem -> {
                    conItem.setExpEleId(expEleId);
                    conItem.setExpId(expId);
                    this.experimentMapper.insertExpEleData((ExpBaseDto)((Object)((Object)((Object)conItem))));
                });
            });
            List<ExpAssetsDto> assetsList = stepItem.getAssetsList();
            assetsList.forEach(assItem -> {
                assItem.setExpId(expId);
                assItem.setStepId(stepId);
                this.experimentMapper.insertExpAssets((ExpAssetsDto)((Object)((Object)assItem)));
            });
        });
        return update;
    }

    @Override
    @Transactional
    public int updateExp(ExpBaseDto expData) {
        int update = this.experimentMapper.updateExp(expData);
        Long expId = expData.getExpId();
        ArrayList<Long> expIds = new ArrayList<Long>();
        expIds.add(expId);
        this.experimentMapper.deleteExpStep(expIds);
        this.experimentMapper.deleteExpEle(expIds);
        this.experimentMapper.deleteExpEleData(expIds);
        this.experimentMapper.deleteExpAssets(expIds);
        List<ExpBaseDto> stepList = expData.getStepList();
        stepList.forEach(stepItem -> {
            stepItem.setExpId(expId);
            this.experimentMapper.insertExpStep((ExpBaseDto)((Object)stepItem));
            Long stepId = stepItem.getStepId();
            List<ExpBaseDto> eleList = stepItem.getEleList();
            eleList.forEach(eleItem -> {
                eleItem.setExpId(expId);
                eleItem.setStepId(stepId);
                this.experimentMapper.insertExpEle((ExpBaseDto)((Object)((Object)eleItem)));
                Long expEleId = eleItem.getExpEleId();
                List<ExpBaseDto> eleContent = eleItem.getEleContent();
                eleContent.forEach(conItem -> {
                    conItem.setExpEleId(expEleId);
                    conItem.setExpId(expId);
                    this.experimentMapper.insertExpEleData((ExpBaseDto)((Object)((Object)((Object)conItem))));
                });
            });
            List<ExpAssetsDto> assetsList = stepItem.getAssetsList();
            assetsList.forEach(assItem -> {
                assItem.setExpId(expId);
                assItem.setStepId(stepId);
                this.experimentMapper.insertExpAssets((ExpAssetsDto)((Object)((Object)assItem)));
            });
        });
        return 1;
    }

    @Override
    public ExpBaseDto queryExpById(Long expId) {
        return this.experimentMapper.queryExpById(expId);
    }

    @Override
    public int deleteExpTableByExpIds(List<ExpBaseDto> data) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        for (ExpBaseDto exp : data) {
            exp.setCreateStaffId(user.getUserId());
            exp.setCreateStaffName(user.getUserName());
        }
        this.expOperateLogMapper.insertExpOperateLog(data);
        ArrayList<Long> expIds = new ArrayList<Long>();
        for (ExpBaseDto exp : data) {
            expIds.add(exp.getExpId());
        }
        return this.experimentMapper.logicalDeleteExpTable(expIds);
    }

    @Override
    public List<ExpBaseDto> selectExpById(Long expId) {
        List<ExpStep> stepList = this.experimentMapper.queryStepById(expId);
        ArrayList<ExpBaseDto> returnList = new ArrayList<ExpBaseDto>();
        stepList.forEach(stepItem -> {
            ExpBaseDto param = new ExpBaseDto();
            Long stepId = stepItem.getStepId();
            List<ExpBaseDto> eLeList = this.experimentMapper.selectExpById(stepId);
            List<ExpAssetsDto> assList = this.experimentMapper.selectExpAssById(stepId);
            Map<Long, List<ExpBaseDto>> eleByExpEleId = eLeList.stream().collect(Collectors.groupingBy(ExpBaseDto::getExpEleId));
            ArrayList<List<ExpBaseDto>> eleList = new ArrayList<List<ExpBaseDto>>(eleByExpEleId.values());
            param.setStepId(stepItem.getStepId());
            param.setStepCode(stepItem.getStepCode());
            param.setStepName(stepItem.getStepName());
            param.setEleArr(eleList);
            param.setAssetsList(assList);
            returnList.add(param);
        });
        log.info("returnList: {}", returnList);
        return returnList;
    }

    @Override
    public int checkExp(ExperimentDto expData) {
        return this.experimentMapper.checkExp(expData);
    }

    @Override
    public int insertResult(ExpBaseDto exp) {
        exp.setExpFinishStatus(2);
        this.experimentMapper.updateExpFinish(exp);
        this.experimentMapper.deleteExpTaskFinish(exp.getExpId());
        this.experimentMapper.insertExpTaskFinish(exp);
        List<ExpBaseDto> stepList = exp.getStepList();
        if (stepList != null && !stepList.isEmpty()) {
            stepList.forEach(stepItem -> this.experimentMapper.updateExpStep((ExpBaseDto)((Object)stepItem)));
        }
        List<SysFileInfo> uploadList = exp.getUploadList();
        if (uploadList != null && !uploadList.isEmpty()) {
            uploadList.forEach(file -> file.setExpId(exp.getExpId()));
            this.experimentMapper.batchSysFileInfo(uploadList);
        }
        return 1;
    }

    @Override
    public ExpBaseDto queryDetailById(Long expId) {
        return this.experimentMapper.queryDetailById(expId);
    }

    @Override
    public List<ExpBaseDto> selectDetailById(Long expId) {
        List<ExpStep> stepList = this.experimentMapper.queryStepDetailById(expId);
        ArrayList<ExpBaseDto> returnList = new ArrayList<ExpBaseDto>();
        stepList.forEach(stepItem -> {
            ExpBaseDto param = new ExpBaseDto();
            Long stepId = stepItem.getStepId();
            List<ExpBaseDto> eLeList = this.experimentMapper.selectExpById(stepId);
            List<ExpAssetsDto> assList = this.experimentMapper.selectExpAssById(stepId);
            Map<Long, List<ExpBaseDto>> eleByExpEleId = eLeList.stream().collect(Collectors.groupingBy(ExpBaseDto::getExpEleId));
            ArrayList<List<ExpBaseDto>> eleList = new ArrayList<List<ExpBaseDto>>(eleByExpEleId.values());
            param.setStepId(stepItem.getStepId());
            param.setStepCode(stepItem.getStepCode());
            param.setStepName(stepItem.getStepName());
            param.setStepBriefEle(stepItem.getStepBriefEle());
            param.setStepResult(stepItem.getStepResult());
            param.setEleArr(eleList);
            param.setAssetsList(assList);
            returnList.add(param);
        });
        log.info("returnList: {}", returnList);
        return returnList;
    }

    @Override
    public List<SysFileInfo> selectSysFileInfoList(Long expId) {
        return this.experimentMapper.selectSysFileInfoList(expId);
    }

    @Override
    public List<List<ExpStep>> selectDetail(ExpBaseDto exp) {
        Long[] expIds = exp.getExpList();
        List<ExpStep> stepList = this.experimentMapper.queryStepList(expIds);
        Map<Long, List<ExpStep>> stepByExpId = stepList.stream().collect(Collectors.groupingBy(ExpStep::getExpId));
        ArrayList<List<ExpStep>> returnList = new ArrayList<List<ExpStep>>(stepByExpId.values());
        return returnList;
    }

    @Override
    @DataScope(deptAlias="d", userAlias="u")
    public Map<String, Object> selectExpStatistics(ExperimentDto exp) {
        return this.experimentMapper.selectExpStatistics(exp);
    }

    @Override
    @DataScope(deptAlias="d", userAlias="u")
    public List<Map<String, Object>> selectExpDeptStatistics(ExperimentDto exp) {
        return this.experimentMapper.selectExpDeptStatistics(exp);
    }

    @Override
    public void cleanDeletedExp() {
        List<ExperimentDto> deletedList = this.experimentMapper.selectDeletedExpList();
        if (deletedList == null || deletedList.isEmpty()) {
            log.info("没有需要清理的已删除实验数据");
            return;
        }
        log.info("开始清理已删除实验数据，共 {} 条", deletedList.size());
        List<Long> expIds = new ArrayList<Long>();
        int fileCount = 0;
        int failFileCount = 0;
        for (ExperimentDto exp : deletedList) {
            Long expId = exp.getExpId();
            expIds.add(expId);
            List<SysFileInfo> fileList = this.experimentMapper.selectSysFileInfoList(expId);
            if (fileList != null && !fileList.isEmpty()) {
                for (SysFileInfo file : fileList) {
                    if (StringUtils.isNotEmpty(file.getFilePath())) {
                        String realPath = RuoYiConfig.getProfile() + FileUtils.stripPrefix(file.getFilePath());
                        boolean deleted = FileUtils.deleteFile(realPath);
                        if (deleted) {
                            fileCount++;
                        } else {
                            failFileCount++;
                            log.warn("文件删除失败: {}", realPath);
                        }
                    }
                }
            }
            this.experimentMapper.deleteSysFileInfoByExpId(expId);
        }
        this.experimentMapper.deleteExpStep(expIds);
        this.experimentMapper.deleteExpEle(expIds);
        this.experimentMapper.deleteExpEleData(expIds);
        this.experimentMapper.deleteExpAssets(expIds);
        this.experimentMapper.deleteExpTableByExpIds(expIds);
        this.experimentMapper.cleanExpOperateLog();
        log.info("已删除实验数据清理完成，清理实验 {} 条，删除文件 {} 个，文件删除失败 {} 个",
                deletedList.size(), fileCount, failFileCount);
    }
}

