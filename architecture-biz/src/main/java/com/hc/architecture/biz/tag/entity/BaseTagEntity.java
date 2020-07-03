package com.hc.architecture.biz.tag.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;

import lombok.Data;

@Data
@TableName("base_tag")
public class BaseTagEntity implements Serializable {

    private static final long serialVersionUID = -719254697154811534L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String tagName;

    private Integer tagType;

    private String tagGroup;

    private String tagKey;

    private Integer tagMappingType;

    private Integer tagWebType;

    private Integer isRange;

    private String remark;

    private String ext;

    private Long createTime;

    private Long opTime;

    @Version
    private Integer lastVer;

    @TableLogic
    private Integer isValid;

}
