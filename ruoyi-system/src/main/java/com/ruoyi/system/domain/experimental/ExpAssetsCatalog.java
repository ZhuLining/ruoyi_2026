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
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ExpAssetsCatalog
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long assetsCatalogId;
    @Excel(name="\u76ee\u5f55\u540d\u79f0")
    private String assetsCatalogName;
    @Excel(name="\u7236\u7ea7\u76ee\u5f55id")
    private Long parentId;
    @Excel(name="\u7236\u7ea7\u76ee\u5f55\u540d\u79f0")
    private String parentName;
    @Excel(name="\u72b6\u6001 10A\u6709\u6548\uff1b10P\u4f5c\u5e9f")
    private String state;
    @Excel(name="\u521b\u5efa\u4eba\u5458ID")
    private Long userId;
    @Excel(name="\u521b\u5efa\u4eba\u5458\u90e8\u95e8ID")
    private Long deptId;
    private List<ExpAssetsCatalog> children = new ArrayList<ExpAssetsCatalog>();

    public Long getAssetsCatalogId() {
        return this.assetsCatalogId;
    }

    public void setAssetsCatalogId(Long assetsCatalogId) {
        this.assetsCatalogId = assetsCatalogId;
    }

    public String getAssetsCatalogName() {
        return this.assetsCatalogName;
    }

    public void setAssetsCatalogName(String assetsCatalogName) {
        this.assetsCatalogName = assetsCatalogName;
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

    public List<ExpAssetsCatalog> getChildren() {
        return this.children;
    }

    public void setChildren(List<ExpAssetsCatalog> children) {
        this.children = children;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.MULTI_LINE_STYLE).append("assetsCatalogId", (Object)this.getAssetsCatalogId()).append("assetsCatalogName", (Object)this.getAssetsCatalogName()).append("parentId", (Object)this.getParentId()).append("parentName", (Object)this.getParentName()).append("state", (Object)this.getState()).append("createBy", (Object)this.getCreateBy()).append("createTime", (Object)this.getCreateTime()).append("updateBy", (Object)this.getUpdateBy()).append("updateTime", (Object)this.getUpdateTime()).append("remark", (Object)this.getRemark()).append("userId", (Object)this.getUserId()).append("deptId", (Object)this.getDeptId()).toString();
    }
}

