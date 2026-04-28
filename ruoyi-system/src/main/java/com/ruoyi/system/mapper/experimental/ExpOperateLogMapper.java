package com.ruoyi.system.mapper.experimental;

import com.ruoyi.system.domain.experimental.ExpBaseDto;
import com.ruoyi.system.domain.experimental.ExpOperateLog;
import java.util.List;

public interface ExpOperateLogMapper {
    public List<ExpOperateLog> selectExpOperateLogList(ExpOperateLog var1);

    public int insertExpOperateLog(List<ExpBaseDto> var1);

    public int recoverExpOperateLog(List<ExpOperateLog> var1);

    public int deleteExpOperateLogById(Long logId);

    public int cleanExpOperateLog();
}
