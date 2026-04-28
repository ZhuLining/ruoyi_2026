/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ruoyi.common.core.domain.entity.SysDept
 *  com.ruoyi.common.core.domain.entity.SysUser
 */
package com.ruoyi.system.mapper.experimental;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import java.util.List;

public interface ExpCommonMapper {
    public List<SysDept> selectDeptList(SysDept var1);

    public List<SysUser> selectUserList(SysUser var1);
}

