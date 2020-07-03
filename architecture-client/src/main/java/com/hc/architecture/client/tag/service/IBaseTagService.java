package com.hc.architecture.client.tag.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.hc.architecture.client.common.page.PageVo;
import com.hc.architecture.client.tag.dto.BaseTagDto;
import com.hc.architecture.client.tag.query.BaseTagQuery;
import com.hc.architecture.client.tag.vo.BaseTagVo;

public interface IBaseTagService {

    Long add(@Valid @NotNull(message = "{tag.obj}{validation.NotNull}") BaseTagDto dto);

    Boolean upd(@NotNull(message = "{tag.id}{validation.NotNull}") Long id,
        @Valid @NotNull(message = "{tag.obj}{validation.NotNull}") BaseTagDto dto);

    Boolean del(@NotNull(message = "{tag.id}{validation.NotNull}") Long id);

    BaseTagVo selectById(@NotNull(message = "{tag.id}{validation.NotNull}") Long id);

    PageVo<BaseTagVo>
        selectByQuery(@Valid @NotNull(message = "{tag.obj.query}{validation.NotNull}") BaseTagQuery query);

}
