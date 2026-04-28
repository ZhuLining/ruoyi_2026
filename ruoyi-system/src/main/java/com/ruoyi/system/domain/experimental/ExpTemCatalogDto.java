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
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ExpTemCatalogDto
extends BaseEntity {
    private Long temCatalogId;
    private String temCatalogName;
    private String temCatalogCode;
    private Long parentId;
    private String parentName;
    private String state;
    private Long userId;
    private Long deptId;
    private List<ExpTemCatalogDto> children = new ArrayList<ExpTemCatalogDto>();

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

    public Long getTemCatalogId() {
        return this.temCatalogId;
    }

    public void setTemCatalogId(Long temCatalogId) {
        this.temCatalogId = temCatalogId;
    }

    public String getTemCatalogName() {
        return this.temCatalogName;
    }

    public void setTemCatalogName(String temCatalogName) {
        this.temCatalogName = temCatalogName;
    }

    public String getTemCatalogCode() {
        return this.temCatalogCode;
    }

    public void setTemCatalogCode(String temCatalogCode) {
        this.temCatalogCode = temCatalogCode;
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

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ExpTemCatalogDto> getChildren() {
        return this.children;
    }

    public void setChildren(List<ExpTemCatalogDto> children) {
        this.children = children;
    }
}

