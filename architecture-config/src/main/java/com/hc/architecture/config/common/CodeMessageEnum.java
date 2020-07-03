package com.hc.architecture.config.common;

/**
 * @Author: hechuan
 * @Date: 2020/5/27 16:48
 * @Description: 返回的编码对应信息
 */
public enum CodeMessageEnum {


    
    PASSWORD_ERROR(601,"user.password.username.error","用户名或密码错误"),
    ADD_USER_ERROR(602,"user.add.error","用户新增失败"),
    UPDATE_USER_ERROR(603,"user.update.error","用户更新失败"),
    UPDATE_USER_PASSWORD_ERROR(604,"user.update.password.error","用户不存在"),
    UPDATE_USER_OLD_PASSWORD_ERROR(605,"user.update.old.password.error","用户旧密码错误"),
    UPDATE_USER_OLD_PASSWORD_NULL(606,"user.update.old.password.null","用户旧密码为空"),
    UPDATE_USER_PASSWORD_NULL(607,"user.update.password.null","新密码为空"),
    RESET_USER_PASSWORD_ERROR(608,"reset.user.password.error","重置密码失败"),
    RESET_USER_PASSWORD_SUCCESS(609,"reset.user.password.success","重置用户密码完成"),
    USER_UN_LOGIN(610,"login.user.token.null","用户未登录"),
    MENU_ADD_ERROR_DUPLICATE(611,"menu.add.error.duplicate","菜单已存在"),
    MENU_ADD_ERROR_INSERT(612,"menu.add.error.insert","插入菜单失败"),
    ROLE_ADD_ERROR_DUPLICATE(613,"role.add.error.duplicate","角色已经存在"),
    ROLE_ADD_ERROR_INSERT(614,"role.add.error.insert","插入角色失败"),
    ROLE_UPDATE_ERROR_UPDATE(615,"role.update.error.update","更新角色失败"),
    ROLE_USER_ERROR_PARAM_NULL(616,"role.user.error.param.null","删除角色用户失败，缺少参数"),
    USER_ROLE_IS_NULL(617,"user.role.is.null","用户没有系统权限"),
    USER_STATUS_IS_DISABLE(618,"user.status.is.disable","用户不可登录"),
    USER_PASSWORD_IS_NOT_NULL(619,"user.password.username.null","用户名或密码不能为空"),
    USER_IS_NOT_EXIST(620,"user.is.not.exist","用户不存在"),
    USER_PASSWORD_ERROR(621,"user.password.error","密码错误"),
    USER_AUTH_ERROR(622,"user.auth.error","用户认证失败");


    //错误码
    private int code;

    //国际化码
    private String i18n;

    //描述
    private String desc;

    CodeMessageEnum(int code, String i18n, String desc) {
        this.code = code;
        this.i18n = i18n;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getI18n() {
        return i18n;
    }

    public String getDesc() {
        return desc;
    }
}
