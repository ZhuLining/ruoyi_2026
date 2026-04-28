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

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ExpTemDto
extends BaseEntity {
    private Long temId;
    private String temName;
    private String temCode;
    private Long temCatalogId;
    private Long createDeptId;
    private Long createStaffId;
    private String createStaffName;
    private Long updateStaffId;
    private String updateStaffName;

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

    public Long getTemCatalogId() {
        return this.temCatalogId;
    }

    public void setTemCatalogId(Long temCatalogId) {
        this.temCatalogId = temCatalogId;
    }

    public Long getCreateDeptId() {
        return this.createDeptId;
    }

    public void setCreateDeptId(Long createDeptId) {
        this.createDeptId = createDeptId;
    }

    public Long getCreateStaffId() {
        return this.createStaffId;
    }

    public void setCreateStaffId(Long createStaffId) {
        this.createStaffId = createStaffId;
    }

    public String getCreateStaffName() {
        return this.createStaffName;
    }

    public void setCreateStaffName(String createStaffName) {
        this.createStaffName = createStaffName;
    }

    public Long getUpdateStaffId() {
        return this.updateStaffId;
    }

    public void setUpdateStaffId(Long updateStaffId) {
        this.updateStaffId = updateStaffId;
    }

    public String getUpdateStaffName() {
        return this.updateStaffName;
    }

    public void setUpdateStaffName(String updateStaffName) {
        this.updateStaffName = updateStaffName;
    }
}

