/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.ruoyi.common.annotation.Excel
 *  com.ruoyi.common.core.domain.BaseEntity
 */
package com.ruoyi.system.domain.experimental;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ExperimentDto
extends BaseEntity {
    private Long expId;
    @Excel(name="\u5b9e\u9a8c\u540d\u79f0")
    private String expName;
    @Excel(name="\u5b9e\u9a8c\u7f16\u7801")
    private String expCode;
    @Excel(name="\u72b6\u6001", readConverterExp="10A=\u6709\u6548,10P=\u4f5c\u5e9f")
    private String expState;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u8ba1\u5212\u5f00\u59cb\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date planStartTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u8ba1\u5212\u7ed3\u675f\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date planEndTime;
    @Excel(name="\u76ee\u5f55ID")
    private Long catalogId;
    @Excel(name="\u5b9e\u9a8c\u5ba1\u6279\u60c5\u51b5", readConverterExp="0=\u5f85\u5ba1\u6838,1=\u5df2\u5ba1\u6838,2=\u5df2\u5426\u51b3,3=\u9000\u56de\u4fee\u6539")
    private Integer expStatus;
    @Excel(name="\u5b9e\u9a8c\u5b8c\u6210\u60c5\u51b5", readConverterExp="0=\u672a\u5f00\u59cb,1=\u5df2\u5b8c\u6210")
    private Integer expFinishStatus;
    @Excel(name="\u521b\u5efa\u8005\u90e8\u95e8id")
    private Long createDeptId;
    @Excel(name="\u521b\u5efa\u8005\u540d\u79f0")
    private String createStaffName;
    @Excel(name="\u521b\u5efa\u8005id")
    private Long createStaffId;
    @Excel(name="\u66f4\u65b0\u8005\u540d\u79f0")
    private String updateStaffName;
    @Excel(name="\u66f4\u65b0\u8005id")
    private Long updateStaffId;
    @Excel(name="\u5ba1\u6838\u5907\u6ce8")
    private String checkRemark;
    @Excel(name="\u5ba1\u6279\u4eba\u540d\u79f0")
    private String checkStaffName;
    @Excel(name="\u5ba1\u6279\u4ebaid")
    private Long checkStaffId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u5b8c\u6210\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date finishDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u521b\u5efa\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u66f4\u65b0\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u5ba1\u6279\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date checkTime;
    private Long[] expIdList;

    public Long[] getExpIdList() {
        return this.expIdList;
    }

    public void setExpIdList(Long[] expIdList) {
        this.expIdList = expIdList;
    }

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

    public String getExpState() {
        return this.expState;
    }

    public void setExpState(String expState) {
        this.expState = expState;
    }

    public Long getCatalogId() {
        return this.catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
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

    public Integer getExpStatus() {
        return this.expStatus;
    }

    public void setExpStatus(Integer expStatus) {
        this.expStatus = expStatus;
    }

    public Integer getExpFinishStatus() {
        return this.expFinishStatus;
    }

    public void setExpFinishStatus(Integer expFinishStatus) {
        this.expFinishStatus = expFinishStatus;
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

    public String getCheckRemark() {
        return this.checkRemark;
    }

    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark;
    }

    public Long getCreateDeptId() {
        return this.createDeptId;
    }

    public void setCreateDeptId(Long createDeptId) {
        this.createDeptId = createDeptId;
    }

    public Date getFinishDate() {
        return this.finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getCheckStaffName() {
        return this.checkStaffName;
    }

    public void setCheckStaffName(String checkStaffName) {
        this.checkStaffName = checkStaffName;
    }

    public Long getCheckStaffId() {
        return this.checkStaffId;
    }

    public void setCheckStaffId(Long checkStaffId) {
        this.checkStaffId = checkStaffId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCheckTime() {
        return this.checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
}

