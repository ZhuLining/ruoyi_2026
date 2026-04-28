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
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ExpElementDto
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long eleId;
    @Excel(name="\u5143\u7d20\u540d\u79f0")
    private String eleName;
    @Excel(name="\u5143\u7d20\u7f16\u7801")
    private String eleCode;
    @Excel(name="\u5143\u7d20\u7c7b\u578b")
    private String eleType;
    @Excel(name="\u5143\u7d20\u9ed8\u8ba4\u503c")
    private String eleDefaultValue;
    private Integer orderNum;
    @Excel(name="\u76ee\u5f55ID")
    private Long catalogId;
    @Excel(name="\u76ee\u5f55\u540d\u79f0")
    private String catalogName;
    @Excel(name="\u76ee\u5f55\u7f16\u7801")
    private String catalogCode;
    private Long parentId;
    @Excel(name="\u7236\u7ea7\u76ee\u5f55\u540d\u79f0")
    private String parentName;
    @Excel(name="\u72b6\u6001 10A\u6709\u6548\uff1b10P\u4f5c\u5e9f")
    private String state;
    private Long userId;
    private Long deptId;
    private List<ExpElementDto> content;

    public String getEleDefaultValue() {
        return this.eleDefaultValue;
    }

    public void setEleDefaultValue(String eleDefaultValue) {
        this.eleDefaultValue = eleDefaultValue;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return this.deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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

    public Long getCatalogId() {
        return this.catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getEleType() {
        return this.eleType;
    }

    public void setEleType(String eleType) {
        this.eleType = eleType;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
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

    public List<ExpElementDto> getContent() {
        return this.content;
    }

    public void setContent(List<ExpElementDto> content) {
        this.content = content;
    }
}

