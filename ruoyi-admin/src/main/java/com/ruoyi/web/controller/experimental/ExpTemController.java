/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ruoyi.common.annotation.Log
 *  com.ruoyi.common.core.controller.BaseController
 *  com.ruoyi.common.core.domain.AjaxResult
 *  com.ruoyi.common.core.domain.entity.SysUser
 *  com.ruoyi.common.core.page.TableDataInfo
 *  com.ruoyi.common.enums.BusinessType
 *  com.ruoyi.common.utils.SecurityUtils
 *  com.ruoyi.common.utils.StringUtils
 *  com.ruoyi.system.domain.experimental.ExpTemBaseDto
 *  com.ruoyi.system.domain.experimental.ExpTemDto
 *  com.ruoyi.system.service.experimental.ExpTemService
 *  com.ruoyi.web.controller.experimental.ExpTemController
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.validation.annotation.Validated
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.ruoyi.web.controller.experimental;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.experimental.ExpTemBaseDto;
import com.ruoyi.system.domain.experimental.ExpTemDto;
import com.ruoyi.system.service.experimental.ExpTemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/experimental/expTem"})
public class ExpTemController
extends BaseController {
    @Autowired
    private ExpTemService expTemService;

    @PreAuthorize(value="@ss.hasPermi('exp:expTemplate:query')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(ExpTemDto tem) {
        this.startPage();
        SysUser user = SecurityUtils.getLoginUser().getUser();
        tem.setCreateStaffId(user.getUserId());
        List list = this.expTemService.queryList(tem);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('exp:expTemplate:add')")
    @Log(title="模版信息", businessType=BusinessType.INSERT)
    @PostMapping(value={"/temAdd"})
    public AjaxResult temAdd(@Validated @RequestBody ExpTemBaseDto tem) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        tem.setCreateStaffId(user.getUserId());
        tem.setCreateStaffName(user.getUserName());
        tem.setCreateDeptId(user.getDeptId());
        return this.toAjax(this.expTemService.insertTem(tem));
    }

    @PreAuthorize(value="@ss.hasPermi('exp:expTemplate:edit')")
    @Log(title="模版信息", businessType=BusinessType.UPDATE)
    @PutMapping(value={"/updateTem"})
    public AjaxResult updateTem(@Validated @RequestBody ExpTemBaseDto data) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        data.setUpdateStaffId(user.getUserId());
        data.setUpdateStaffName(user.getUserName());
        return this.toAjax(this.expTemService.updateTem(data));
    }

    @PreAuthorize(value="@ss.hasPermi('exp:expTemplate:remove')")
    @Log(title="实验管理", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{temIds}"})
    public AjaxResult remove(@PathVariable Long[] temIds) {
        return this.toAjax(this.expTemService.deleteExpTableByTemIds(temIds));
    }

    @GetMapping(value={"/", "/{temId}"})
    public AjaxResult getInfo(@PathVariable(value="temId", required=false) Long temId) {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull((Object)temId)) {
            ExpTemBaseDto temData = this.expTemService.queryTemById(temId);
            List stepData = this.expTemService.selectTemById(temId);
            ajax.put("data", (Object)temData);
            ajax.put("stepList", (Object)stepData);
        }
        return ajax;
    }
}

