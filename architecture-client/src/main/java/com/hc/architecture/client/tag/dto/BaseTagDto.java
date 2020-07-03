package com.hc.architecture.client.tag.dto;

import java.io.Serializable;

import javax.validation.constraints.*;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class BaseTagDto implements Serializable {
    private static final long serialVersionUID = 1770555624900166001L;

    @NotEmpty(message = "{tag.tagName}{validation.NotEmpty}")
    private String tagName;

    @NotNull(message = "{tag.tagType}{validation.NotNull}")
    @Range(min = 0, max = 1, message = "{tag.tagType}{validation.Range}")
    private Integer tagType;

    @NotEmpty(message = "{tag.tagGroup}{validation.NotEmpty}")
    private String tagGroup;

    @NotEmpty(message = "{tag.tagKey}{validation.NotEmpty}")
    private String tagKey;

    private Integer tagMappingType;

    private Integer tagWebType;

    private Integer isRange;

    private String remark;

    private String ext;

    private Integer lastVer;

}
