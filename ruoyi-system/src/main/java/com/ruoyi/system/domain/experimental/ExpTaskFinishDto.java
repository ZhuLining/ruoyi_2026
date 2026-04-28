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
import java.util.Date;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ExpTaskFinishDto
extends BaseEntity {
    private Long finishId;
    private Long expId;
    private Long staffId;
    private String staffName;
    private Long orgId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date finishDate;
    private String result;

    public Long getFinishId() {
        return this.finishId;
    }

    public void setFinishId(Long finishId) {
        this.finishId = finishId;
    }

    public Long getExpId() {
        return this.expId;
    }

    public void setExpId(Long expId) {
        this.expId = expId;
    }

    public Long getStaffId() {
        return this.staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return this.staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getFinishDate() {
        return this.finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

