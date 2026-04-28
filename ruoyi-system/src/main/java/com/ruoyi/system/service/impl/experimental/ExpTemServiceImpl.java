/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.ruoyi.system.service.experimental.impl;

import com.ruoyi.system.domain.experimental.ExpAssetsDto;
import com.ruoyi.system.domain.experimental.ExpStep;
import com.ruoyi.system.domain.experimental.ExpTemBaseDto;
import com.ruoyi.system.domain.experimental.ExpTemDto;
import com.ruoyi.system.mapper.experimental.ExpTemMapper;
import com.ruoyi.system.service.experimental.ExpTemService;
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
public class ExpTemServiceImpl
implements ExpTemService {
    private static final Logger log = LoggerFactory.getLogger(ExpTemServiceImpl.class);
    @Autowired
    private ExpTemMapper expTemMapper;

    @Override
    @Transactional
    public int insertTem(ExpTemBaseDto tem) {
        int update = this.expTemMapper.insertTem(tem);
        Long temId = tem.getTemId();
        log.info("temId: {}", (Object)temId);
        List<ExpTemBaseDto> stepList = tem.getStepList();
        stepList.forEach(stepItem -> {
            stepItem.setTemId(temId);
            this.expTemMapper.insertTemStep((ExpTemBaseDto)((Object)stepItem));
            Long stepId = stepItem.getStepId();
            List<ExpTemBaseDto> eleList = stepItem.getEleList();
            eleList.forEach(eleItem -> {
                eleItem.setTemId(temId);
                eleItem.setStepId(stepId);
                this.expTemMapper.insertExpEle((ExpTemBaseDto)((Object)((Object)eleItem)));
                Long expEleId = eleItem.getExpEleId();
                List<ExpTemBaseDto> eleContent = eleItem.getEleContent();
                eleContent.forEach(conItem -> {
                    conItem.setExpEleId(expEleId);
                    conItem.setTemId(temId);
                    this.expTemMapper.insertExpEleData((ExpTemBaseDto)((Object)((Object)((Object)conItem))));
                });
            });
            List<ExpAssetsDto> assetsList = stepItem.getAssetsList();
            assetsList.forEach(assItem -> {
                assItem.setTemId(temId);
                assItem.setStepId(stepId);
                this.expTemMapper.insertExpAssets((ExpAssetsDto)((Object)((Object)assItem)));
            });
        });
        return 1;
    }

    @Override
    public ExpTemBaseDto queryTemById(Long temId) {
        return this.expTemMapper.queryTemById(temId);
    }

    @Override
    public List<ExpTemBaseDto> selectTemById(Long temId) {
        List<ExpStep> stepList = this.expTemMapper.queryStepById(temId);
        ArrayList<ExpTemBaseDto> returnList = new ArrayList<ExpTemBaseDto>();
        stepList.forEach(stepItem -> {
            ExpTemBaseDto param = new ExpTemBaseDto();
            Long stepId = stepItem.getStepId();
            List<ExpTemBaseDto> eLeList = this.expTemMapper.selectExpById(stepId);
            List<ExpAssetsDto> assList = this.expTemMapper.selectExpAssById(stepId);
            Map<Long, List<ExpTemBaseDto>> eleByExpEleId = eLeList.stream().collect(Collectors.groupingBy(ExpTemBaseDto::getExpEleId));
            ArrayList<List<ExpTemBaseDto>> eleList = new ArrayList<List<ExpTemBaseDto>>(eleByExpEleId.values());
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
    @Transactional
    public int updateTem(ExpTemBaseDto temData) {
        int update = this.expTemMapper.updateTem(temData);
        Long temId = temData.getTemId();
        Long[] temIds = new Long[]{temId};
        this.expTemMapper.deleteExpStep(temIds);
        this.expTemMapper.deleteExpEle(temIds);
        this.expTemMapper.deleteExpEleData(temIds);
        this.expTemMapper.deleteExpAssets(temIds);
        List<ExpTemBaseDto> stepList = temData.getStepList();
        stepList.forEach(stepItem -> {
            stepItem.setTemId(temId);
            this.expTemMapper.insertTemStep((ExpTemBaseDto)((Object)stepItem));
            Long stepId = stepItem.getStepId();
            List<ExpTemBaseDto> eleList = stepItem.getEleList();
            eleList.forEach(eleItem -> {
                eleItem.setTemId(temId);
                eleItem.setStepId(stepId);
                this.expTemMapper.insertExpEle((ExpTemBaseDto)((Object)((Object)eleItem)));
                Long expEleId = eleItem.getExpEleId();
                List<ExpTemBaseDto> eleContent = eleItem.getEleContent();
                eleContent.forEach(conItem -> {
                    conItem.setExpEleId(expEleId);
                    conItem.setTemId(temId);
                    this.expTemMapper.insertExpEleData((ExpTemBaseDto)((Object)((Object)((Object)conItem))));
                });
            });
            List<ExpAssetsDto> assetsList = stepItem.getAssetsList();
            assetsList.forEach(assItem -> {
                assItem.setTemId(temId);
                assItem.setStepId(stepId);
                this.expTemMapper.insertExpAssets((ExpAssetsDto)((Object)((Object)assItem)));
            });
        });
        return 1;
    }

    @Override
    public int deleteExpTableByTemIds(Long[] temIds) {
        this.expTemMapper.deleteExpStep(temIds);
        this.expTemMapper.deleteExpEle(temIds);
        this.expTemMapper.deleteExpEleData(temIds);
        this.expTemMapper.deleteExpAssets(temIds);
        return this.expTemMapper.deleteExpTableByTemIds(temIds);
    }

    @Override
    public List<ExpTemDto> queryList(ExpTemDto tem) {
        return this.expTemMapper.queryList(tem);
    }
}

