/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.ruoyi.common.annotation.Excel
 *  com.ruoyi.common.annotation.Excel$ColumnType
 *  com.ruoyi.common.core.domain.BaseEntity
 */
package com.ruoyi.system.domain.experimental;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

public class AssetsDto
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Excel(name="\u76ee\u5f55ID")
    private Long assetsCatalogId;
    @Excel(name="\u76ee\u5f55\u540d\u79f0")
    private String assetsCatalogName;
    @Excel(name="\u8d44\u4ea7\u5e8f\u53f7", cellType=Excel.ColumnType.NUMERIC, prompt="\u8d44\u4ea7\u7f16\u53f7")
    private Long assetsId;
    @Excel(name="\u8d44\u4ea7\u540d\u79f0")
    private String assetsName;
    @Excel(name="\u8d44\u4ea7\u7c7b\u578b")
    private String assetsType;
    @Excel(name="\u8d44\u4ea7\u6570\u91cf")
    private Long assetsNumber;
    @Excel(name="\u8d44\u4ea7\u72b6\u6001")
    private String status;
    @Excel(name="\u5df2\u51fa\u5e93\u603b\u6570\u91cf")
    private Long useTotalNumber;
    @Excel(name="\u672a\u51fa\u5e93\u6570\u91cf")
    private Long unusedNumber;
    @Excel(name="\u6700\u4f4e\u5e93\u5b58")
    private Long assetsThreshold;
    private String supplierType;
    private String assetsModel;
    private Long assetsStoreId;
    private String assetsStoreName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="\u4fdd\u8d28\u671f", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date assetsExpirationDate;
    @Excel(name="\u51fa\u5e93\u4eba\u540d\u79f0")
    private String userName;
    private Long userId;
    private Long deptId;

    public Long getDeptId() {
        return this.deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getAssetsId() {
        return this.assetsId;
    }

    public void setAssetsId(Long assetsId) {
        this.assetsId = assetsId;
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

    public Long getAssetsNumber() {
        return this.assetsNumber;
    }

    public void setAssetsNumber(Long assetsNumber) {
        this.assetsNumber = assetsNumber;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUseTotalNumber() {
        return this.useTotalNumber;
    }

    public void setUseTotalNumber(Long useTotalNumber) {
        this.useTotalNumber = useTotalNumber;
    }

    public Long getUnusedNumber() {
        return this.unusedNumber;
    }

    public void setUnusedNumber(Long unusedNumber) {
        this.unusedNumber = unusedNumber;
    }

    public Long getAssetsThreshold() {
        return this.assetsThreshold;
    }

    public void setAssetsThreshold(Long assetsThreshold) {
        this.assetsThreshold = assetsThreshold;
    }

    public String getSupplierType() {
        return this.supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public String getAssetsModel() {
        return this.assetsModel;
    }

    public void setAssetsModel(String assetsModel) {
        this.assetsModel = assetsModel;
    }

    public Long getAssetsStoreId() {
        return this.assetsStoreId;
    }

    public void setAssetsStoreId(Long assetsStoreId) {
        this.assetsStoreId = assetsStoreId;
    }

    public String getAssetsStoreName() {
        return this.assetsStoreName;
    }

    public void setAssetsStoreName(String assetsStoreName) {
        this.assetsStoreName = assetsStoreName;
    }

    public Date getAssetsExpirationDate() {
        return this.assetsExpirationDate;
    }

    public void setAssetsExpirationDate(Date assetsExpirationDate) {
        this.assetsExpirationDate = assetsExpirationDate;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

