/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ruoyi.common.annotation.DataScope
 *  com.ruoyi.common.core.domain.TreeSelect
 *  com.ruoyi.common.core.domain.entity.SysDept
 *  com.ruoyi.common.core.domain.entity.SysUser
 *  com.ruoyi.common.utils.StringUtils
 *  com.ruoyi.common.utils.spring.SpringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.ruoyi.system.service.experimental.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.mapper.experimental.ExpCommonMapper;
import com.ruoyi.system.service.experimental.ExpCommonService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpCommonServiceImpl
implements ExpCommonService {
    private static final Logger log = LoggerFactory.getLogger(ExpCommonServiceImpl.class);
    @Autowired
    private ExpCommonMapper expCommonMapper;

    @Override
    @DataScope(deptAlias="d", userAlias="u")
    public List<SysUser> selectUserList(SysUser user) {
        return this.expCommonMapper.selectUserList(user);
    }

    @Override
    @DataScope(deptAlias="d")
    public List<SysDept> selectDeptList(SysDept dept) {
        return this.expCommonMapper.selectDeptList(dept);
    }

    @Override
    public List<TreeSelect> selectDeptTreeList(SysDept dept) {
        List<SysDept> depts = ((ExpCommonServiceImpl)SpringUtils.getAopProxy((Object)this)).selectDeptList(dept);
        return this.buildDeptTreeSelect(depts);
    }

    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts) {
        List<SysDept> deptTrees = this.buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts) {
        List<SysDept> returnList = new ArrayList<SysDept>();
        List tempList = depts.stream().map(SysDept::getDeptId).collect(Collectors.toList());
        for (SysDept dept : depts) {
            if (tempList.contains(dept.getParentId())) continue;
            this.recursionFn(depts, dept);
            returnList.add(dept);
        }
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    private void recursionFn(List<SysDept> list, SysDept t) {
        List<SysDept> childList = this.getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList) {
            if (!this.hasChild(list, tChild)) continue;
            this.recursionFn(list, tChild);
        }
    }

    private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
        ArrayList<SysDept> tlist = new ArrayList<SysDept>();
        for (SysDept n : list) {
            if (!StringUtils.isNotNull((Object)n.getParentId()) || n.getParentId().longValue() != t.getDeptId().longValue()) continue;
            tlist.add(n);
        }
        return tlist;
    }

    private boolean hasChild(List<SysDept> list, SysDept t) {
        return this.getChildList(list, t).size() > 0;
    }
}

