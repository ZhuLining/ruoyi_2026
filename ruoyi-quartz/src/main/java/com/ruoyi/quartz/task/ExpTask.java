package com.ruoyi.quartz.task;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.service.experimental.ExperimentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 实验模块定时任务
 */
@Component("expTask")
public class ExpTask {

    private static final Logger log = LoggerFactory.getLogger(ExpTask.class);

    /**
     * 清空已删除的实验数据（包含附件文件）
     * invokeTarget: expTask.cleanDeletedExp()
     */
    public void cleanDeletedExp() {
        ExperimentService experimentService = SpringUtils.getBean(ExperimentService.class);
        if (experimentService == null) {
            log.error("ExperimentService 获取失败，清理任务终止");
            return;
        }
        experimentService.cleanDeletedExp();
    }
}
