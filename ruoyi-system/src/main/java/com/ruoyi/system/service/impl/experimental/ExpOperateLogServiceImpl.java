package com.ruoyi.system.service.impl.experimental;

import com.ruoyi.system.domain.experimental.ExpOperateLog;
import com.ruoyi.system.mapper.experimental.ExpOperateLogMapper;
import com.ruoyi.system.mapper.experimental.ExperimentMapper;
import com.ruoyi.system.service.experimental.ExpOperateLogService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpOperateLogServiceImpl
implements ExpOperateLogService {
    @Autowired
    private ExpOperateLogMapper expOperateLogMapper;
    @Autowired
    private ExperimentMapper experimentMapper;

    @Override
    public List<ExpOperateLog> selectExpOperateLogList(ExpOperateLog expOperateLog) {
        return this.expOperateLogMapper.selectExpOperateLogList(expOperateLog);
    }

    @Override
    public int recoverExp(List<ExpOperateLog> logs) {
        ArrayList<Long> expIds = new ArrayList<Long>();
        for (ExpOperateLog log : logs) {
            expIds.add(log.getExpId());
        }
        this.experimentMapper.logicalRecoverExpTable(expIds);
        return this.expOperateLogMapper.recoverExpOperateLog(logs);
    }

    @Override
    public int deleteExpOperateLogById(Long logId) {
        return this.expOperateLogMapper.deleteExpOperateLogById(logId);
    }

    @Override
    public int cleanExpOperateLog() {
        return this.expOperateLogMapper.cleanExpOperateLog();
    }
}
