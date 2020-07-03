package com.hc.architecture;

import com.alibaba.fastjson.JSON;
import com.hc.architecture.biz.permission.bo.UserMenuBo;
import com.hc.architecture.biz.permission.bo.UserPermissionBo;
import com.hc.architecture.biz.permission.service.MenuService;
import com.hc.architecture.biz.permission.service.UserInfoService;
import com.hc.architecture.config.common.util.RedisUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/** 
* ApiApplication Tester. 
* 
* @author <Authors name> 
* @since <pre>7月 3, 2020</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@MapperScan("com.hc.architecture.biz.**.mapper")
@SpringBootTest(classes = {ApiApplication.class})
public class ApiApplicationTest {

    private static final Logger logger = LoggerFactory.getLogger(ApiApplicationTest.class);
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MenuService menuService;

    public ApiApplicationTest() {
    }

    @Test
    public void testLock(){

        String key = "sku20102021113";
        String value = "ereasss9saa9as";

        boolean result = redisUtil.lock(key,value);

        Assert.assertTrue(result);
    }

    @Test
    public void testUnLock() {
        String key = "sku20102021112";
        String value = "ereasss9saa9as";
        boolean result = redisUtil.unlock(key,value);

        Assert.assertTrue(result);
    }


    @Test
    public void testDelKey() {
        String key = "18768143759";

        boolean result = redisUtil.delKey(key);
        Assert.assertTrue(result);
    }



    @Test
    public void testAddHash() {
        String key = "18768143759";
        String hashKey = "age";
        String hashValue = "18";
        boolean result = redisUtil.hset(key,hashKey,hashValue);
        Assert.assertTrue(result);
    }

    @Test
    public void testgetHash() {
        String key = "18768143759";
        String hashKey = "age";
        String result = (String) redisUtil.hget(key,hashKey);
        logger.info(hashKey +"="+result);
        Assert.assertNotNull(result);
    }

    @Test
    public void testUserInfoService() {

        List<UserPermissionBo> rolePermission = userInfoService.getRolePermission(1);

        for (UserPermissionBo per : rolePermission) {
            System.out.println(per.getMenuName());
        }
    }

    @Test
    public void testUserTree() {

        List<UserMenuBo> userMenuBos = menuService.getUserMenu(1);

        System.out.println(JSON.toJSONString(userMenuBos));
    }


} 
