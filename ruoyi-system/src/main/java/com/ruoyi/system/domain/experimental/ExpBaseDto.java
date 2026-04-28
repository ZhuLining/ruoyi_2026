/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.ruoyi.common.core.domain.BaseEntity
 */
package com.ruoyi.system.domain.experimental;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.system.domain.experimental.ExpAssetsDto;
import com.ruoyi.system.domain.SysFileInfo;
import java.util.Date;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ExpBaseDto
extends BaseEntity {
    private Long id;
    private Long expId;
    private Long expEleId;
    private String expName;
    private String expCode;
    private Long stepId;
    private String stepName;
    private String stepCode;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date planStartTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date planEndTime;
    private Long createDeptId;
    private String createStaffName;
    private Long createStaffId;
    private String updateStaffName;
    private Long updateStaffId;
    private Long catalogId;
    private String catalogName;
    private String catalogCode;
    private Long parentId;
    private String parentName;
    private Long eleId;
    private String eleName;
    private String eleCode;
    private String eleType;
    private String eleValue;
    private List<ExpBaseDto> content;
    private List<ExpBaseDto> eleList;
    private List<ExpAssetsDto> assetsList;
    private List<ExpBaseDto> eleContent;
    private List<List<ExpBaseDto>> eleArr;
    private List<ExpBaseDto> stepList;
    private Long[] expList;
    private List<SysFileInfo> uploadList;
    private Integer expFinishStatus;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date finishDate;
    private String stepBriefEle;
    private String stepResult;
    private String result;

    public Long getExpId() {
        return this.expId;
    }

    public void setExpId(Long expId) {
        this.expId = expId;
    }

    public String getExpName() {
        return this.expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpCode() {
        return this.expCode;
    }

    public void setExpCode(String expCode) {
        this.expCode = expCode;
    }

    public Long getCatalogId() {
        return this.catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCatalogCode() {
        return this.catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    public Long getEleId() {
        return this.eleId;
    }

    public void setEleId(Long eleId) {
        this.eleId = eleId;
    }

    public String getEleName() {
        return this.eleName;
    }

    public void setEleName(String eleName) {
        this.eleName = eleName;
    }

    public String getEleCode() {
        return this.eleCode;
    }

    public void setEleCode(String eleCode) {
        this.eleCode = eleCode;
    }

    public String getEleType() {
        return this.eleType;
    }

    public void setEleType(String eleType) {
        this.eleType = eleType;
    }

    public List<ExpBaseDto> getContent() {
        return this.content;
    }

    public void setContent(List<ExpBaseDto> content) {
        this.content = content;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getEleValue() {
        return this.eleValue;
    }

    public void setEleValue(String eleValue) {
        this.eleValue = eleValue;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ExpBaseDto> getEleList() {
        return this.eleList;
    }

    public void setEleList(List<ExpBaseDto> eleList) {
        this.eleList = eleList;
    }

    public List<ExpBaseDto> getEleContent() {
        return this.eleContent;
    }

    public void setEleContent(List<ExpBaseDto> eleContent) {
        this.eleContent = eleContent;
    }

    public Long getExpEleId() {
        return this.expEleId;
    }

    public void setExpEleId(Long expEleId) {
        this.expEleId = expEleId;
    }

    public Date getPlanStartTime() {
        return this.planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanEndTime() {
        return this.planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public List<ExpAssetsDto> getAssetsList() {
        return this.assetsList;
    }

    public void setAssetsList(List<ExpAssetsDto> assetsList) {
        this.assetsList = assetsList;
    }

    public Long getStepId() {
        return this.stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public String getStepName() {
        return this.stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getStepCode() {
        return this.stepCode;
    }

    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }

    public List<ExpBaseDto> getStepList() {
        return this.stepList;
    }

    public void setStepList(List<ExpBaseDto> stepList) {
        this.stepList = stepList;
    }

    public String getCreateStaffName() {
        return this.createStaffName;
    }

    public void setCreateStaffName(String createStaffName) {
        this.createStaffName = createStaffName;
    }

    public Long getCreateStaffId() {
        return this.createStaffId;
    }

    public void setCreateStaffId(Long createStaffId) {
        this.createStaffId = createStaffId;
    }

    public String getUpdateStaffName() {
        return this.updateStaffName;
    }

    public void setUpdateStaffName(String updateStaffName) {
        this.updateStaffName = updateStaffName;
    }

    public Long getUpdateStaffId() {
        return this.updateStaffId;
    }

    public void setUpdateStaffId(Long updateStaffId) {
        this.updateStaffId = updateStaffId;
    }

    public Long getCreateDeptId() {
        return this.createDeptId;
    }

    public void setCreateDeptId(Long createDeptId) {
        this.createDeptId = createDeptId;
    }

    public Integer getExpFinishStatus() {
        return this.expFinishStatus;
    }

    public void setExpFinishStatus(Integer expFinishStatus) {
        this.expFinishStatus = expFinishStatus;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStepResult() {
        return this.stepResult;
    }

    public void setStepResult(String stepResult) {
        this.stepResult = stepResult;
    }

    public Date getFinishDate() {
        return this.finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public List<List<ExpBaseDto>> getEleArr() {
        return this.eleArr;
    }

    public void setEleArr(List<List<ExpBaseDto>> eleArr) {
        this.eleArr = eleArr;
    }

    public String getStepBriefEle() {
        return this.stepBriefEle;
    }

    public void setStepBriefEle(String stepBriefEle) {
        this.stepBriefEle = stepBriefEle;
    }

    public List<SysFileInfo> getUploadList() {
        return this.uploadList;
    }

    public void setUploadList(List<SysFileInfo> uploadList) {
        this.uploadList = uploadList;
    }

    public Long[] getExpList() {
        return this.expList;
    }

    public void setExpList(Long[] expList) {
        this.expList = expList;
    }
}

