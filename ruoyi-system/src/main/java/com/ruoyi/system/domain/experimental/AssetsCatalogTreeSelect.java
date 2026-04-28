/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.ruoyi.system.domain.experimental;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.system.domain.experimental.ExpAssetsCatalog;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class AssetsCatalogTreeSelect
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String label;
    @JsonInclude(value=JsonInclude.Include.NON_EMPTY)
    private List<AssetsCatalogTreeSelect> children;

    public AssetsCatalogTreeSelect() {
    }

    public AssetsCatalogTreeSelect(ExpAssetsCatalog catalog) {
        this.id = catalog.getAssetsCatalogId();
        this.label = catalog.getAssetsCatalogName();
        this.children = catalog.getChildren().stream().map(AssetsCatalogTreeSelect::new).collect(Collectors.toList());
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<AssetsCatalogTreeSelect> getChildren() {
        return this.children;
    }

    public void setChildren(List<AssetsCatalogTreeSelect> children) {
        this.children = children;
    }
}

