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
 *  com.ruoyi.system.domain.experimental.ExpBaseDto
 *  com.ruoyi.system.domain.experimental.ExperimentDto
 *  com.ruoyi.system.service.experimental.ExperimentService
 *  com.ruoyi.web.controller.common.ExportExcel
 *  com.ruoyi.web.controller.experimental.ExperimentController
 *  jakarta.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.validation.annotation.Validated
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
import com.ruoyi.system.domain.experimental.ExpBaseDto;
import com.ruoyi.system.domain.experimental.ExpStep;
import com.ruoyi.system.domain.experimental.ExperimentDto;
import com.ruoyi.system.service.experimental.ExperimentService;
import com.ruoyi.web.controller.common.ExportExcel;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/experimental/expManage"})
public class ExperimentController
extends BaseController {
    @Autowired
    private ExperimentService experimentService;

    @PreAuthorize(value="@ss.hasPermi('exp:expManage:query')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(ExperimentDto exp) {
        this.startPage();
        List list = this.experimentService.queryList(exp);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('exp:expManage:query')")
    @GetMapping(value={"/backlog"})
    public TableDataInfo queryBacklogList(ExperimentDto exp) {
        this.startPage();
        List list = this.experimentService.queryUnfinishList(exp);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('exp:expManage:query')")
    @GetMapping(value={"/finishList"})
    public TableDataInfo queryFinishList(ExperimentDto exp) {
        this.startPage();
        List list = this.experimentService.queryFinishList(exp);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('exp:expManage:list')")
    @GetMapping(value={"/expList"})
    public AjaxResult queryExpList(ExpBaseDto data) {
        return this.success((Object)this.experimentService.queryExpList(data));
    }

    @PreAuthorize(value="@ss.hasPermi('exp:expManage:add')")
    @Log(title="实验信息", businessType=BusinessType.INSERT)
    @PostMapping(value={"/expAdd"})
    public AjaxResult expAdd(@Validated @RequestBody ExpBaseDto exp) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        exp.setCreateStaffId(user.getUserId());
        exp.setCreateStaffName(user.getUserName());
        exp.setCreateDeptId(user.getDeptId());
        return this.toAjax(this.experimentService.insertExp(exp));
    }

    @PreAuthorize(value="@ss.hasPermi('exp:expManage:edit')")
    @Log(title="实验信息", businessType=BusinessType.UPDATE)
    @PutMapping(value={"/updateExp"})
    public AjaxResult updateExp(@Validated @RequestBody ExpBaseDto data) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        data.setUpdateStaffId(user.getUserId());
        data.setUpdateStaffName(user.getUserName());
        return this.toAjax(this.experimentService.updateExp(data));
    }

    @GetMapping(value={"/", "/{expId}"})
    public AjaxResult getInfo(@PathVariable(value="expId", required=false) Long expId) {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull((Object)expId)) {
            ExpBaseDto expData = this.experimentService.queryExpById(expId);
            List stepData = this.experimentService.selectExpById(expId);
            ajax.put("data", (Object)expData);
            ajax.put("stepList", (Object)stepData);
        }
        return ajax;
    }

    @PreAuthorize(value="@ss.hasPermi('exp:expManage:remove')")
    @Log(title="实验管理", businessType=BusinessType.UPDATE)
    @PostMapping(value={"/del"})
    public AjaxResult remove(@Validated @RequestBody List<ExpBaseDto> exp) {
        return this.toAjax(this.experimentService.deleteExpTableByExpIds(exp));
    }

    @PreAuthorize(value="@ss.hasPermi('exp:expManage:check')")
    @Log(title="实验审核", businessType=BusinessType.UPDATE)
    @PutMapping(value={"/check"})
    public AjaxResult expCheck(@Validated @RequestBody ExperimentDto exp) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        exp.setCheckStaffId(user.getUserId());
        exp.setCheckStaffName(user.getUserName());
        return this.toAjax(this.experimentService.checkExp(exp));
    }

    @PreAuthorize(value="@ss.hasPermi('exp:expManage:result')")
    @Log(title="实验结果", businessType=BusinessType.UPDATE)
    @PutMapping(value={"/result"})
    public AjaxResult expResult(@Validated @RequestBody ExpBaseDto exp) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        exp.setCreateStaffId(user.getUserId());
        exp.setCreateStaffName(user.getUserName());
        exp.setCreateDeptId(user.getDeptId());
        return this.toAjax(this.experimentService.insertResult(exp));
    }

    @GetMapping(value={"/detail/{expId}"})
    public AjaxResult queryDetail(@PathVariable(value="expId") Long expId) {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull((Object)expId)) {
            ExpBaseDto expData = this.experimentService.queryDetailById(expId);
            List stepData = this.experimentService.selectDetailById(expId);
            List fileData = this.experimentService.selectSysFileInfoList(expId);
            ajax.put("data", (Object)expData);
            ajax.put("stepList", (Object)stepData);
            ajax.put("fileData", (Object)fileData);
        }
        return ajax;
    }

    @Log(title="实验信息", businessType=BusinessType.EXPORT)
    @PreAuthorize(value="@ss.hasPermi('exp:expManage:export')")
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, ExpBaseDto exp) {
        if (exp.getExpId() != null && (exp.getExpList() == null || exp.getExpList().length == 0)) {
            exp.setExpList(new Long[]{exp.getExpId()});
        }
        if (exp.getExpList() == null || exp.getExpList().length == 0) {
            return;
        }
        List<List<ExpStep>> stepData = this.experimentService.selectDetail(exp);
        ExportExcel exportExcel = new ExportExcel();
        exportExcel.exportData(response, stepData);
    }
}

