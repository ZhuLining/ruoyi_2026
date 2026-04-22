package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 资产出库记录表 exp_assets_use
 *
 * @author ruoyi
 */
public class ExpAssetsUse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 出库ID */
    private Long useId;

    /** 资产ID */
    private Long assetsId;

    /** 出库数量 */
    private Long useNumber;

    /** 出库人ID */
    private Long userId;

    /** 出库人名称 */
    private String userName;

    public Long getUseId()
    {
        return useId;
    }

    public void setUseId(Long useId)
    {
        this.useId = useId;
    }

    public Long getAssetsId()
    {
        return assetsId;
    }

    public void setAssetsId(Long assetsId)
    {
        this.assetsId = assetsId;
    }

    public Long getUseNumber()
    {
        return useNumber;
    }

    public void setUseNumber(Long useNumber)
    {
        this.useNumber = useNumber;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("useId", getUseId())
            .append("assetsId", getAssetsId())
            .append("useNumber", getUseNumber())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
