/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ruoyi.common.annotation.Excel
 *  com.ruoyi.common.core.domain.BaseEntity
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.ruoyi.system.domain.experimental;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ExpOperateLog
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long logId;
    @Excel(name="实验ID")
    private Long expId;
    @Excel(name="实验名称")
    private String expName;
    @Excel(name="操作者id")
    private Long operateStaffId;
    @Excel(name="操作者名称")
    private String operateStaffName;
    @Excel(name="操作类型，0-删除")
    private Long operationType;

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getLogId() {
        return this.logId;
    }

    public void setExpId(Long expId) {
        this.expId = expId;
    }

    public Long getExpId() {
        return this.expId;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpName() {
        return this.expName;
    }

    public void setOperateStaffId(Long operateStaffId) {
        this.operateStaffId = operateStaffId;
    }

    public Long getOperateStaffId() {
        return this.operateStaffId;
    }

    public void setOperateStaffName(String operateStaffName) {
        this.operateStaffName = operateStaffName;
    }

    public String getOperateStaffName() {
        return this.operateStaffName;
    }

    public void setOperationType(Long operationType) {
        this.operationType = operationType;
    }

    public Long getOperationType() {
        return this.operationType;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.MULTI_LINE_STYLE).append("logId", (Object)this.getLogId()).append("expId", (Object)this.getExpId()).append("expName", (Object)this.getExpName()).append("operateStaffId", (Object)this.getOperateStaffId()).append("operateStaffName", (Object)this.getOperateStaffName()).append("operationType", (Object)this.getOperationType()).append("createTime", (Object)this.getCreateTime()).toString();
    }
}

