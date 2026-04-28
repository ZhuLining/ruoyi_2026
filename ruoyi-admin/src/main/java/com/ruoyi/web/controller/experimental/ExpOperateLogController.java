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
 *  com.ruoyi.system.domain.experimental.ExpOperateLog
 *  com.ruoyi.system.service.experimental.ExpOperateLogService
 *  com.ruoyi.web.controller.experimental.ExpOperateLogController
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.validation.annotation.Validated
 *  org.springframework.web.bind.annotation.GetMapping
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
import com.ruoyi.system.domain.experimental.ExpOperateLog;
import com.ruoyi.system.service.experimental.ExpOperateLogService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/experimental/expLog"})
public class ExpOperateLogController
extends BaseController {
    @Autowired
    private ExpOperateLogService expOperateLogService;

    @PreAuthorize(value="@ss.hasPermi('exp:expLogManage:query')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(ExpOperateLog expOperateLog) {
        this.startPage();
        List list = this.expOperateLogService.selectExpOperateLogList(expOperateLog);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('exp:expLogManage:remove')")
    @Log(title="实验日志", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{logId}"})
    public AjaxResult remove(@PathVariable Long logId) {
        return this.toAjax(this.expOperateLogService.deleteExpOperateLogById(logId));
    }

    @PreAuthorize(value="@ss.hasPermi('exp:expLogManage:remove')")
    @Log(title="实验日志", businessType=BusinessType.CLEAN)
    @DeleteMapping(value={"/clean"})
    public AjaxResult clean() {
        return this.toAjax(this.expOperateLogService.cleanExpOperateLog());
    }

    @PreAuthorize(value="@ss.hasPermi('exp:expLogManage:restore')")
    @Log(title="实验信息", businessType=BusinessType.UPDATE)
    @PutMapping(value={"/recover"})
    public AjaxResult updateExp(@Validated @RequestBody List<ExpOperateLog> logs) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        return this.toAjax(this.expOperateLogService.recoverExp(logs));
    }
}

