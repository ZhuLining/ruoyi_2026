/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.ruoyi.common.annotation.Excel
 *  com.ruoyi.common.core.domain.BaseEntity
 */
package com.ruoyi.system.domain.experimental;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ExpEleDataDto
extends BaseEntity {
    private Long id;
    @Excel(name="\u5b9e\u9a8c\u5143\u7d20ID")
    private Long expEleId;
    @Excel(name="\u5b9e\u9a8cID")
    private Long expId;
    @Excel(name="\u5b9e\u9a8c\u7f16\u7801")
    private String expCode;
    @Excel(name="\u76ee\u5f55ID")
    private Long catalogId;
    @Excel(name="\u5143\u7d20ID")
    private Long eleId;
    private String expValue;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExpEleId() {
        return this.expEleId;
    }

    public void setExpEleId(Long expEleId) {
        this.expEleId = expEleId;
    }

    public Long getExpId() {
        return this.expId;
    }

    public void setExpId(Long expId) {
        this.expId = expId;
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

    public Long getEleId() {
        return this.eleId;
    }

    public void setEleId(Long eleId) {
        this.eleId = eleId;
    }

    public String getExpValue() {
        return this.expValue;
    }

    public void setExpValue(String expValue) {
        this.expValue = expValue;
    }
}

