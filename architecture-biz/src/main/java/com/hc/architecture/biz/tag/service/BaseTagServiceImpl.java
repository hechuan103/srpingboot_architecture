package com.hc.architecture.biz.tag.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.architecture.biz.tag.entity.BaseTagEntity;
import com.hc.architecture.biz.tag.mapper.BaseTagMapper;
import com.hc.architecture.client.common.page.PageVo;
import com.hc.architecture.client.tag.dto.BaseTagDto;
import com.hc.architecture.client.tag.query.BaseTagQuery;
import com.hc.architecture.client.tag.service.IBaseTagService;
import com.hc.architecture.client.tag.vo.BaseTagVo;
import com.hc.architecture.config.common.exception.BizException;

@Service
@Validated
public class BaseTagServiceImpl extends ServiceImpl<BaseTagMapper, BaseTagEntity> implements IBaseTagService {

    // TODO: 2020/6/5 国际化资源文件

    @Override
    public Long add(BaseTagDto dto) {
        // TODO: 2020/6/5 validate 必须字段，业务逻辑

        // TODO: 2020/6/5 redis锁 ，防重

        // 名称重复
        BaseTagEntity existedEntity =
            lambdaQuery().eq(BaseTagEntity::getTagName, dto.getTagName()).last("limit 1").one();
        if (existedEntity != null) {
            throw new BizException("tag.tagname.existed", "标签名称已存在", "标签模块");
        }

        // group + key 重复
        existedEntity = lambdaQuery().eq(BaseTagEntity::getTagGroup, dto.getTagGroup())
            .eq(BaseTagEntity::getTagKey, dto.getTagKey()).last("limit 1").one();
        if (existedEntity != null) {
            throw new BizException("tag.groupkey.existed", "在该标签组中key已存在", "标签模块");
        }

        BaseTagEntity entity = new BaseTagEntity();
        BeanUtils.copyProperties(dto, entity);
        save(entity);

        return entity.getId();
    }

    @Override
    public Boolean upd(Long id, BaseTagDto dto) {

        // TODO: 2020/6/5 id 校验

        BaseTagEntity entity = getById(id);
        if (entity == null) {
            throw new BizException("tag.not.exists", "标签不存在或已被删除", "标签模块");
        }
        // 当前版本
        Integer curVer = entity.getLastVer();

        BeanUtils.copyProperties(dto, entity);
        entity.setOpTime(System.currentTimeMillis());
        // 如果未传入版本
        if (entity.getLastVer() == null) {
            entity.setLastVer(curVer);
        }

        if (updateById(entity)) {
            return true;
        } else {
            throw new BizException("tag.updated", "标签已更新，请获取最新数据后再操作", "标签模块");
        }
    }

    @Override
    public Boolean del(Long id) {

        // TODO: 2020/6/5 id 校验
        removeById(id);
        return true;
    }

    @Override
    public BaseTagVo selectById(Long id) {
        BaseTagEntity entity = getById(id);
        if (entity == null) {
            throw new BizException("tag.not.exists", "标签不存在或已被删除", "标签模块");
        }
        BaseTagVo vo = new BaseTagVo();
        BeanUtils.copyProperties(entity, vo);
        vo.setId(String.valueOf(id));
        return vo;
    }

    @Override
    public PageVo<BaseTagVo> selectByQuery(BaseTagQuery query) {
        return null;
    }
}
