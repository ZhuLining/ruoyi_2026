/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ruoyi.common.annotation.Excel
 *  com.ruoyi.common.core.domain.BaseEntity
 */
package com.ruoyi.system.domain.experimental;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class AssetsUseDto
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long useId;
    @Excel(name="\u8d44\u4ea7ID")
    private Long assetsId;
    @Excel(name="\u8d44\u4ea7\u540d\u79f0")
    private String assetsName;
    @Excel(name="\u8d44\u4ea7\u7c7b\u578b")
    private String assetsType;
    @Excel(name="\u51fa\u5e93\u6570\u91cf")
    private Long useNumber;
    @Excel(name="\u51fa\u5e93\u4ebaID")
    private Long userId;
    @Excel(name="\u51fa\u5e93\u4eba\u540d\u79f0")
    private String userName;
    @Excel(name="\u51fa\u5e93\u603b\u6570\u91cf")
    private Long useTotalNumber;

    public void setUseId(Long useId) {
        this.useId = useId;
    }

    public Long getUseId() {
        return this.useId;
    }

    public void setAssetsId(Long assetsId) {
        this.assetsId = assetsId;
    }

    public Long getAssetsId() {
        return this.assetsId;
    }

    public void setUseNumber(Long useNumber) {
        this.useNumber = useNumber;
    }

    public Long getUseNumber() {
        return this.useNumber;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public Long getUseTotalNumber() {
        return this.useTotalNumber;
    }

    public void setUseTotalNumber(Long useTotalNumber) {
        this.useTotalNumber = useTotalNumber;
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
}

