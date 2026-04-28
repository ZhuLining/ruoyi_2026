/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ruoyi.common.core.domain.TreeSelect
 *  com.ruoyi.common.core.domain.entity.SysDept
 *  com.ruoyi.common.core.domain.entity.SysUser
 */
package com.ruoyi.system.service.experimental;

import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import java.util.List;

public interface ExpCommonService {
    public List<SysUser> selectUserList(SysUser var1);

    public List<SysDept> selectDeptList(SysDept var1);

    public List<TreeSelect> selectDeptTreeList(SysDept var1);

    public List<SysDept> buildDeptTree(List<SysDept> var1);

    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> var1);
}

