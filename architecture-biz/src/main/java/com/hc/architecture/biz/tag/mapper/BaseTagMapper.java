package com.hc.architecture.biz.tag.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hc.architecture.biz.tag.entity.BaseTagEntity;

@Mapper
public interface BaseTagMapper extends BaseMapper<BaseTagEntity> {}
