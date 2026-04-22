package com.ruoyi.system.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 资产信息表 exp_assets
 *
 * @author ruoyi
 */
public class ExpAssets extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 资产ID */
    private Long assetsId;

    /** 资产名称 */
    private String assetsName;

    /** 资产类型 */
    private String assetsType;

    /** 资产数量 */
    private Long assetsNumber;

    /** 已出库总数量 */
    private Long useTotalNumber;

    /** 最低库存 */
    private Long assetsThreshold;

    /** 供应商 */
    private String supplierType;

    /** 资产规格 */
    private String assetsModel;

    /** 保管人id */
    private Long assetsStoreId;

    /** 保管人名称 */
    private String assetsStoreName;

    /** 保质期 */
    private Date assetsExpirationDate;

    /** 资产所属目录 */
    private Long assetsCatalogId;

    /** 资产状态 10A有效；10P作废 */
    private String status;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    /** 创建人员ID */
    private Long userId;

    /** 创建人员部门ID */
    private Long deptId;

    public Long getAssetsId()
    {
        return assetsId;
    }

    public void setAssetsId(Long assetsId)
    {
        this.assetsId = assetsId;
    }

    @NotBlank(message = "资产名称不能为空")
    public String getAssetsName()
    {
        return assetsName;
    }

    public void setAssetsName(String assetsName)
    {
        this.assetsName = assetsName;
    }

    @NotBlank(message = "资产类型不能为空")
    public String getAssetsType()
    {
        return assetsType;
    }

    public void setAssetsType(String assetsType)
    {
        this.assetsType = assetsType;
    }

    @NotNull(message = "资产数量不能为空")
    public Long getAssetsNumber()
    {
        return assetsNumber;
    }

    public void setAssetsNumber(Long assetsNumber)
    {
        this.assetsNumber = assetsNumber;
    }

    public Long getUseTotalNumber()
    {
        return useTotalNumber;
    }

    public void setUseTotalNumber(Long useTotalNumber)
    {
        this.useTotalNumber = useTotalNumber;
    }

    public Long getAssetsThreshold()
    {
        return assetsThreshold;
    }

    public void setAssetsThreshold(Long assetsThreshold)
    {
        this.assetsThreshold = assetsThreshold;
    }

    @NotBlank(message = "供应商不能为空")
    public String getSupplierType()
    {
        return supplierType;
    }

    public void setSupplierType(String supplierType)
    {
        this.supplierType = supplierType;
    }

    public String getAssetsModel()
    {
        return assetsModel;
    }

    public void setAssetsModel(String assetsModel)
    {
        this.assetsModel = assetsModel;
    }

    public Long getAssetsStoreId()
    {
        return assetsStoreId;
    }

    public void setAssetsStoreId(Long assetsStoreId)
    {
        this.assetsStoreId = assetsStoreId;
    }

    public String getAssetsStoreName()
    {
        return assetsStoreName;
    }

    public void setAssetsStoreName(String assetsStoreName)
    {
        this.assetsStoreName = assetsStoreName;
    }

    public Date getAssetsExpirationDate()
    {
        return assetsExpirationDate;
    }

    public void setAssetsExpirationDate(Date assetsExpirationDate)
    {
        this.assetsExpirationDate = assetsExpirationDate;
    }

    public Long getAssetsCatalogId()
    {
        return assetsCatalogId;
    }

    public void setAssetsCatalogId(Long assetsCatalogId)
    {
        this.assetsCatalogId = assetsCatalogId;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
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
            .append("assetsId", getAssetsId())
            .append("assetsName", getAssetsName())
            .append("assetsType", getAssetsType())
            .append("assetsNumber", getAssetsNumber())
            .append("useTotalNumber", getUseTotalNumber())
            .append("assetsThreshold", getAssetsThreshold())
            .append("supplierType", getSupplierType())
            .append("assetsModel", getAssetsModel())
            .append("assetsStoreId", getAssetsStoreId())
            .append("assetsStoreName", getAssetsStoreName())
            .append("assetsExpirationDate", getAssetsExpirationDate())
            .append("assetsCatalogId", getAssetsCatalogId())
            .append("status", getStatus())
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
