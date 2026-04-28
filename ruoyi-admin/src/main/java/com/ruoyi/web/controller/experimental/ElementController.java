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
 *  com.ruoyi.system.domain.experimental.ExpElementDto
 *  com.ruoyi.system.service.experimental.ElementService
 *  com.ruoyi.web.controller.experimental.ElementController
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
import com.ruoyi.system.domain.experimental.ExpElementDto;
import com.ruoyi.system.service.experimental.ElementService;
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
@RequestMapping(value={"/experimental/element"})
public class ElementController
extends BaseController {
    @Autowired
    private ElementService elementService;

    @PreAuthorize(value="@ss.hasPermi('exp:element:query')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(ExpElementDto ele) {
        this.startPage();
        SysUser user = SecurityUtils.getLoginUser().getUser();
        ele.setUserId(user.getUserId());
        List list = this.elementService.queryList(ele);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('exp:element:add')")
    @Log(title="元素信息", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ExpElementDto ele) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        ele.setUserId(user.getUserId());
        ele.setCreateBy(user.getUserName());
        ele.setDeptId(user.getDeptId());
        return this.toAjax(this.elementService.insertData(ele));
    }

    @PreAuthorize(value="@ss.hasPermi('exp:element:edit')")
    @Log(title="元素信息", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ExpElementDto ele) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        ele.setUpdateBy(user.getUserName());
        return this.toAjax(this.elementService.updateElem(ele));
    }

    @PreAuthorize(value="@ss.hasPermi('exp:element:remove')")
    @Log(title="元素信息", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{eleIds}"})
    public AjaxResult remove(@PathVariable Long[] eleIds) {
        return this.toAjax(this.elementService.deleteElemByIds(eleIds));
    }
}

