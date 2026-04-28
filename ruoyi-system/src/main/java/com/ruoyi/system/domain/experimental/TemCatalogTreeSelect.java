/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.ruoyi.system.domain.experimental;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.system.domain.experimental.ExpTemCatalogDto;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class TemCatalogTreeSelect
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String label;
    @JsonInclude(value=JsonInclude.Include.NON_EMPTY)
    private List<TemCatalogTreeSelect> children;

    public TemCatalogTreeSelect() {
    }

    public TemCatalogTreeSelect(ExpTemCatalogDto catalog) {
        this.id = catalog.getTemCatalogId();
        this.label = catalog.getTemCatalogName();
        this.children = catalog.getChildren().stream().map(TemCatalogTreeSelect::new).collect(Collectors.toList());
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

    public List<TemCatalogTreeSelect> getChildren() {
        return this.children;
    }

    public void setChildren(List<TemCatalogTreeSelect> children) {
        this.children = children;
    }
}

