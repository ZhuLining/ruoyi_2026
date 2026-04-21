package com.ruoyi.system.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 元素表 ele_table
 *
 * @author ruoyi
 */
public class EleTable extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 元素ID */
    private Long eleId;

    /** 元素名称 */
    private String eleName;

    /** 元素编码 */
    private String eleCode;

    /** 所属目录ID */
    private Long catalogId;

    /** 元素类型 */
    private String eleType;

    /** 元素默认值 */
    private String eleDefaultValue;

    /** 显示顺序 */
    private Integer orderNum;

    /** 状态 10A有效；10P作废 */
    private String state;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    /** 创建人员ID */
    private Long userId;

    /** 创建人员部门ID */
    private Long deptId;

    public Long getEleId()
    {
        return eleId;
    }

    public void setEleId(Long eleId)
    {
        this.eleId = eleId;
    }

    @NotBlank(message = "元素名称不能为空")
    @Size(min = 0, max = 100, message = "元素名称长度不能超过100个字符")
    public String getEleName()
    {
        return eleName;
    }

    public void setEleName(String eleName)
    {
        this.eleName = eleName;
    }

    public String getEleCode()
    {
        return eleCode;
    }

    public void setEleCode(String eleCode)
    {
        this.eleCode = eleCode;
    }

    public Long getCatalogId()
    {
        return catalogId;
    }

    public void setCatalogId(Long catalogId)
    {
        this.catalogId = catalogId;
    }

    public String getEleType()
    {
        return eleType;
    }

    public void setEleType(String eleType)
    {
        this.eleType = eleType;
    }

    public String getEleDefaultValue()
    {
        return eleDefaultValue;
    }

    public void setEleDefaultValue(String eleDefaultValue)
    {
        this.eleDefaultValue = eleDefaultValue;
    }

    public Integer getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("eleId", getEleId())
            .append("eleName", getEleName())
            .append("eleCode", getEleCode())
            .append("catalogId", getCatalogId())
            .append("eleType", getEleType())
            .append("eleDefaultValue", getEleDefaultValue())
            .append("orderNum", getOrderNum())
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
