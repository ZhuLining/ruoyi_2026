/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ruoyi.common.core.controller.BaseController
 *  com.ruoyi.common.core.domain.AjaxResult
 *  com.ruoyi.common.core.domain.entity.SysDept
 *  com.ruoyi.common.core.domain.entity.SysUser
 *  com.ruoyi.common.core.page.TableDataInfo
 *  com.ruoyi.system.service.experimental.ExpCommonService
 *  com.ruoyi.web.controller.experimental.ExpCommonController
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.ruoyi.web.controller.experimental;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.experimental.ExpTemDto;
import com.ruoyi.system.service.experimental.ExpCommonService;
import com.ruoyi.system.service.experimental.ExpTemService;
import com.ruoyi.system.service.experimental.ExpSopTemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/experimental/common"})
public class ExpCommonController
extends BaseController {
    @Autowired
    private ExpCommonService expCommonService;

    @Autowired
    private ExpTemService expTemService;

    @Autowired
    private ExpSopTemService expSopTemService;

    @GetMapping(value={"/list"})
    public TableDataInfo list(SysUser user) {
        this.startPage();
        List list = this.expCommonService.selectUserList(user);
        return this.getDataTable(list);
    }

    @GetMapping(value={"/deptTree"})
    public AjaxResult deptTree(SysDept dept) {
        return this.success((Object)this.expCommonService.selectDeptTreeList(dept));
    }

    @GetMapping(value={"/listAllTem"})
    public AjaxResult listAllTem(ExpTemDto query) {
        return this.success((Object)this.expTemService.queryList(query));
    }

    @GetMapping(value={"/listAllSopTem"})
    public AjaxResult listAllSopTem(ExpTemDto query) {
        return this.success((Object)this.expSopTemService.queryList(query));
    }
}

