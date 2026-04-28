/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ruoyi.common.core.domain.BaseEntity
 */
package com.ruoyi.system.domain.experimental;

import com.ruoyi.common.core.domain.BaseEntity;

public class ExpAssetsDto
extends BaseEntity {
    private Long assetsCatalogId;
    private String assetsCatalogName;
    private Long expAssetsId;
    private Long expId;
    private Long temId;
    private Long stepId;
    private Long assetsId;
    private String assetsName;
    private String assetsType;
    private Long expAssetsNumber;

    public Long getExpAssetsId() {
        return this.expAssetsId;
    }

    public void setExpAssetsId(Long expAssetsId) {
        this.expAssetsId = expAssetsId;
    }

    public Long getAssetsId() {
        return this.assetsId;
    }

    public void setAssetsId(Long assetsId) {
        this.assetsId = assetsId;
    }

    public Long getExpId() {
        return this.expId;
    }

    public void setExpId(Long expId) {
        this.expId = expId;
    }

    public Long getExpAssetsNumber() {
        return this.expAssetsNumber;
    }

    public void setExpAssetsNumber(Long expAssetsNumber) {
        this.expAssetsNumber = expAssetsNumber;
    }

    public String getAssetsName() {
        return this.assetsName;
    }

    public void setAssetsName(String assetsName) {
        this.assetsName = assetsName;
    }

    public String getAssetsType() {
        return this.assetsType;
    }

    public void setAssetsType(String assetsType) {
        this.assetsType = assetsType;
    }

    public Long getStepId() {
        return this.stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public Long getTemId() {
        return this.temId;
    }

    public void setTemId(Long temId) {
        this.temId = temId;
    }

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
}

