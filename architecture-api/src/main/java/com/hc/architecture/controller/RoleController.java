package com.hc.architecture.controller;

import com.hc.architecture.biz.permission.bo.RoleToUserBo;
import com.hc.architecture.biz.permission.vo.AuthorRoleMenuVo;
import com.hc.architecture.biz.permission.entity.Role;
import com.hc.architecture.biz.permission.service.RoleService;
import com.hc.architecture.biz.permission.vo.RoleVo;
import com.hc.architecture.config.common.ApiResult;
import com.hc.architecture.config.common.CodeMessageEnum;
import com.hc.architecture.config.common.page.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @Author: hechuan
 * @Date: 2020/5/29 14:19
 * @Description: 角色管理 api
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @PostMapping("/roleList")
    public ApiResult<Object> getRoleList(@RequestBody RoleVo roleVo) {
        PageList<Role> roleList = roleService.getRoleList(roleVo);

        return ApiResult.success(roleList);
    }


    @PostMapping("/addRole")
    public ApiResult<String> addRole(@RequestBody RoleVo roleVo) {
        return roleService.addRole(roleVo);
    }

    @PostMapping("/enableRole")
    public ApiResult<String> enableRole(@RequestBody RoleVo roleVo) {

        int value = roleService.enableRole(roleVo);
        if (value > 0) {
            return ApiResult.success();
        }
        return ApiResult.error();
    }

    @PostMapping("/updateRole")
    public ApiResult<String> updateRole(@RequestBody RoleVo roleVo) {

        return roleService.updateRole(roleVo);
    }


    @PostMapping("/authMenu")
    public ApiResult<String> authMenu(@RequestBody AuthorRoleMenuVo menuBo) {

        if (Optional.ofNullable(menuBo).isPresent()) {
            if (menuBo.getMenuIds().size() < 0) {
                return ApiResult.error();
            } else {
                roleService.authorRole(menuBo);
                return ApiResult.success();
            }
        }
        return ApiResult.error();
    }

    @PostMapping("/roleUserList")
    public ApiResult<Object> getRoleUserList(@RequestBody RoleVo roleVo) {

        PageList<RoleToUserBo> roleUser = roleService.getRoleUser(roleVo);

        return ApiResult.success(roleUser);
    }


    @PostMapping("/deleteRoleUser")
    public ApiResult<String> deleteRoleUser(@RequestBody RoleVo roleVo) {
        if (roleVo.getId() != null && roleVo.getUserId() != null) {
            int value = roleService.deleteRoleUser(roleVo);
            if (value > 0) {
                return ApiResult.success();
            }
        } else {
            return ApiResult.error(CodeMessageEnum.ROLE_USER_ERROR_PARAM_NULL.getI18n());
        }
        return ApiResult.error();
    }


    @GetMapping("/getAllRole")
    public ApiResult getAllRole() {
        return ApiResult.success(roleService.getAllRole());
    }

}
