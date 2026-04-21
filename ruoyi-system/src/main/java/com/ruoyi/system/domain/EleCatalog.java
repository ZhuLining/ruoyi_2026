package com.ruoyi.system.domain;

import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 元素目录 ele_catalog
 *
 * @author ruoyi
 */
public class EleCatalog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 目录ID */
    private Long catalogId;

    /** 父目录ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 目录名称 */
    private String catalogName;

    /** 显示顺序 */
    private Integer orderNum;

    /** 目录编码 */
    private String catalogCode;

    /** 状态 10A有效；10P作废 */
    private String state;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    /** 创建人员ID */
    private Long userId;

    /** 创建人员部门ID */
    private Long deptId;

    /** 子目录 */
    private List<EleCatalog> children = new ArrayList<EleCatalog>();

    public Long getCatalogId()
    {
        return catalogId;
    }

    public void setCatalogId(Long catalogId)
    {
        this.catalogId = catalogId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getAncestors()
    {
        return ancestors;
    }

    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    @NotBlank(message = "目录名称不能为空")
    @Size(min = 0, max = 50, message = "目录名称长度不能超过50个字符")
    public String getCatalogName()
    {
        return catalogName;
    }

    public void setCatalogName(String catalogName)
    {
        this.catalogName = catalogName;
    }

    @NotNull(message = "显示顺序不能为空")
    public Integer getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    public String getCatalogCode()
    {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode)
    {
        this.catalogCode = catalogCode;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public List<EleCatalog> getChildren()
    {
        return children;
    }

    public void setChildren(List<EleCatalog> children)
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("catalogId", getCatalogId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("catalogName", getCatalogName())
            .append("orderNum", getOrderNum())
            .append("catalogCode", getCatalogCode())
            .append("state", getState())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .toString();
    }
}
