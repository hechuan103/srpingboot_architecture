package com.hc.architecture.config.common;

/**
 * @Author: hechuan
 * @Date: 2020/5/28 10:45
 * @Description: 菜单类型枚举
 */
public enum MenuLevelEnum {

    FIRST_LEVEL_MENU_TYPE(1, "一级菜单","system.menu.level.first"),
    SECOND_LEVEL_MENU_TYPE(2, "二级菜单","system.menu.level.second"),
    THIRD_LEVEL_MENU_TYPE(3, "三级菜单","system.menu.level.third"),
    FOURTH_LEVEL_MENU_TYPE(4, "功能(按钮)","system.menu.level.fourth");

    private int level;
    private String des;
    private String i18nCode;


    MenuLevelEnum(int level, String des, String i18nCode) {
        this.level = level;
        this.des = des;
        this.i18nCode = i18nCode;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getI18nCode() {
        return i18nCode;
    }

    public void setI18nCode(String i18nCode) {
        this.i18nCode = i18nCode;
    }
}
