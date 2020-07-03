package com.hc.architecture.client.tag.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseTagVo implements Serializable {
    private static final long serialVersionUID = -4244008606218085411L;

    private String id;

    private String tagName;

    private Integer tagType;

    private String tagGroup;

    private String tagKey;

    private Integer tagMappingType;

    private Integer tagWebType;

    private Integer isRange;

    private String remark;

    private String ext;

    private Integer lastVer;
}
