/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 *  org.springframework.transaction.annotation.Transactional
 */
package com.ruoyi.system.service.experimental.impl;

import com.ruoyi.system.domain.experimental.ExpElementDto;
import com.ruoyi.system.mapper.experimental.ElementMapper;
import com.ruoyi.system.service.experimental.ElementService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ElementServiceImpl
implements ElementService {
    private static final Logger log = LoggerFactory.getLogger(ElementServiceImpl.class);
    @Autowired
    private ElementMapper elementMapper;

    @Override
    public List<ExpElementDto> queryList(ExpElementDto ele) {
        return this.elementMapper.queryList(ele);
    }

    @Override
    public int insertData(ExpElementDto data) {
        return this.elementMapper.insertData(data);
    }

    @Override
    @Transactional
    public int updateElem(ExpElementDto ele) {
        return this.elementMapper.updateElem(ele);
    }

    @Override
    @Transactional
    public int deleteElemByIds(Long[] eleIds) {
        return this.elementMapper.deleteElemByIds(eleIds);
    }
}

