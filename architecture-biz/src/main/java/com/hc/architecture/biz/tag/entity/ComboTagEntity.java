package com.hc.architecture.biz.tag.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;

import lombok.Data;

@Data
@TableName("combo_tag")
public class ComboTagEntity implements Serializable {
    private static final long serialVersionUID = -4279223401458434326L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String city;

    private String tagName;

    private String tagGroup;

    private Integer resType;

    private Integer resCount;

    private String expression;

    private String remark;

    private String ext;

    private Long createTime;

    private Long createUserId;

    private Long opTime;

    private Long opUserId;

    @Version
    private Integer lastVer;

    @TableLogic
    private Integer isValid;
}
