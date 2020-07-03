package com.hc.architecture.client.tag.enums;

public enum TagTypeEnum {

    PROP(0, "属性标签"), CALC(1, "计算标签");

    TagTypeEnum(int val, String name) {
        this.val = val;
        this.name = name;
    }

    private int val;

    private String name;

    public int getVal() {
        return val;
    }

    public String getName() {
        return name;
    }
}
