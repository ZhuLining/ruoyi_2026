/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.ruoyi.common.core.domain.BaseEntity
 */
package com.ruoyi.system.domain.experimental;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.system.domain.experimental.ExpAssetsDto;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ExpTemBaseDto
extends BaseEntity {
    private Long id;
    private Long temId;
    private String temName;
    private String temCode;
    private Long temCatalogId;
    private Long stepId;
    private String stepName;
    private String stepCode;
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
    private Long expEleId;
    private Long eleId;
    private String eleName;
    private String eleCode;
    private String eleType;
    private String eleValue;
    private List<ExpTemBaseDto> content;
    private List<ExpTemBaseDto> eleList;
    private List<ExpAssetsDto> assetsList;
    private List<ExpTemBaseDto> eleContent;
    private List<List<ExpTemBaseDto>> eleArr;
    private List<ExpTemBaseDto> stepList;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemId() {
        return this.temId;
    }

    public void setTemId(Long temId) {
        this.temId = temId;
    }

    public String getTemName() {
        return this.temName;
    }

    public void setTemName(String temName) {
        this.temName = temName;
    }

    public String getTemCode() {
        return this.temCode;
    }

    public void setTemCode(String temCode) {
        this.temCode = temCode;
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

    public Long getCreateDeptId() {
        return this.createDeptId;
    }

    public void setCreateDeptId(Long createDeptId) {
        this.createDeptId = createDeptId;
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

    public Long getExpEleId() {
        return this.expEleId;
    }

    public void setExpEleId(Long expEleId) {
        this.expEleId = expEleId;
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

    public String getEleValue() {
        return this.eleValue;
    }

    public void setEleValue(String eleValue) {
        this.eleValue = eleValue;
    }

    public List<ExpTemBaseDto> getContent() {
        return this.content;
    }

    public void setContent(List<ExpTemBaseDto> content) {
        this.content = content;
    }

    public List<ExpTemBaseDto> getEleList() {
        return this.eleList;
    }

    public void setEleList(List<ExpTemBaseDto> eleList) {
        this.eleList = eleList;
    }

    public List<ExpAssetsDto> getAssetsList() {
        return this.assetsList;
    }

    public void setAssetsList(List<ExpAssetsDto> assetsList) {
        this.assetsList = assetsList;
    }

    public List<ExpTemBaseDto> getEleContent() {
        return this.eleContent;
    }

    public void setEleContent(List<ExpTemBaseDto> eleContent) {
        this.eleContent = eleContent;
    }

    public List<ExpTemBaseDto> getStepList() {
        return this.stepList;
    }

    public void setStepList(List<ExpTemBaseDto> stepList) {
        this.stepList = stepList;
    }

    public Long getTemCatalogId() {
        return this.temCatalogId;
    }

    public void setTemCatalogId(Long temCatalogId) {
        this.temCatalogId = temCatalogId;
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

    public List<List<ExpTemBaseDto>> getEleArr() {
        return this.eleArr;
    }

    public void setEleArr(List<List<ExpTemBaseDto>> eleArr) {
        this.eleArr = eleArr;
    }
}

