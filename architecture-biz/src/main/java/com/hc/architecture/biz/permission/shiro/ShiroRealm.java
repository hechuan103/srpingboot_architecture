package com.hc.architecture.biz.permission.shiro;

import com.hc.architecture.biz.permission.bo.UserPermissionBo;
import com.hc.architecture.biz.permission.entity.User;
import com.hc.architecture.biz.permission.service.UserInfoService;
import com.hc.architecture.config.common.exception.BizException;
import com.hc.architecture.config.common.util.EncryptionUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ShiroRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    public ShiroRealm() {
    }

    @Override
    public void setName(String name) {
        super.setName("shiroRealm");
    }

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获取登录用户名
        try {
            AccountProfile accountProfile = (AccountProfile) principalCollection.getPrimaryPrincipal();
            //获取用户的权限
            List<UserPermissionBo> rolePermission = userInfoService.getRolePermission(accountProfile.getId());
            for (UserPermissionBo userPermissionBo : rolePermission) {
                //设置角色和菜单对应的权限
                simpleAuthorizationInfo.addRole(userPermissionBo.getRoleCode());
                simpleAuthorizationInfo.addStringPermission(userPermissionBo.getMenuCode());
            }
        } catch (Exception biz) {
            logger.error("授权异常", biz);
            throw new BizException("get.user.author.error", "用户授权异常", "ShiroRealm");
        }

        return simpleAuthorizationInfo;
    }

    /**
     * 登录认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("shiro 登录认证 >>");
        // 从登录接口中拿到用户名密码
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        String userPwd = new String(userToken.getPassword());
        String userName = userToken.getUsername();
        //根据用户名从数据库获取用户
        User user =  userInfoService.userLogin(userName);
        Optional<User> optionalUser = Optional.ofNullable(user);
        //校验密码
        if (optionalUser.isPresent()) {
            String oldPass = user.getPassword();
            String salt = user.getSalt();
            if (EncryptionUtil.decryption(userPwd, oldPass, salt)) {
                AccountProfile profile = new AccountProfile();
                BeanUtils.copyProperties(user,profile);
                profile.setUserName(user.getName());
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(profile, userPwd, getName());
                return info;
            } else {
                throw new IncorrectCredentialsException();
            }
        } else {
            throw new UnknownAccountException();
        }
    }

}
