/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.ruoyi.common.annotation.Excel
 *  com.ruoyi.common.core.domain.BaseEntity
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.ruoyi.system.domain.experimental;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ExpStep
extends BaseEntity {
    private Long stepId;
    @Excel(name="\u5b9e\u9a8cID")
    private Long expId;
    @Excel(name="\u5b9e\u9a8c\u540d\u79f0")
    private String expName;
    @Excel(name="\u5b9e\u9a8c\u6b65\u9aa4\u540d\u79f0")
    private String stepName;
    @Excel(name="\u5b9e\u9a8c\u6b65\u9aa4\u7f16\u7801")
    private String stepCode;
    @Excel(name="\u6a21\u7248ID")
    private Long temId;
    @Excel(name="\u6b65\u9aa4\u7b80\u8981\u8981\u7d20\u8bf4\u660e")
    private String stepBriefEle;
    @Excel(name="\u6b65\u9aa4\u7ed3\u679c\u8bf4\u660e")
    private String stepResult;
    @Excel(name="\u521b\u5efa\u8005\u540d\u79f0")
    private String createStaffName;

    public Long getStepId() {
        return this.stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public Long getExpId() {
        return this.expId;
    }

    public void setExpId(Long expId) {
        this.expId = expId;
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

    public void setTemId(Long temId) {
        this.temId = temId;
    }

    public Long getTemId() {
        return this.temId;
    }

    public String getStepResult() {
        return this.stepResult;
    }

    public void setStepResult(String stepResult) {
        this.stepResult = stepResult;
    }

    public String getStepBriefEle() {
        return this.stepBriefEle;
    }

    public void setStepBriefEle(String stepBriefEle) {
        this.stepBriefEle = stepBriefEle;
    }

    public String getExpName() {
        return this.expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getCreateStaffName() {
        return this.createStaffName;
    }

    public void setCreateStaffName(String createStaffName) {
        this.createStaffName = createStaffName;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.MULTI_LINE_STYLE).append("stepId", (Object)this.getStepId()).append("expId", (Object)this.getExpId()).append("stepName", (Object)this.getStepName()).append("stepCode", (Object)this.getStepCode()).append("createBy", (Object)this.getCreateBy()).append("createTime", (Object)this.getCreateTime()).append("updateBy", (Object)this.getUpdateBy()).append("updateTime", (Object)this.getUpdateTime()).append("remark", (Object)this.getRemark()).append("temId", (Object)this.getTemId()).append("stepBriefEle", (Object)this.getStepBriefEle()).append("stepResult", (Object)this.getStepResult()).toString();
    }
}

