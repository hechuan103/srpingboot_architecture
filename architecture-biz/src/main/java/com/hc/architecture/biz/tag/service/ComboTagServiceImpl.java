package com.hc.architecture.biz.tag.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.architecture.biz.tag.entity.ComboTagEntity;
import com.hc.architecture.biz.tag.mapper.ComboTagMapper;
import com.hc.architecture.client.tag.service.IComboTagService;
import org.springframework.stereotype.Service;

@Service
public class ComboTagServiceImpl extends ServiceImpl<ComboTagMapper, ComboTagEntity> implements IComboTagService {

    @Override
    public Long add() {

        ComboTagEntity entity = new ComboTagEntity();
        entity.setCity("112");

        save(entity);

        return entity.getId();
    }
}
