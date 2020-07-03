package com.hc.architecture.config.common.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @Author: hechuan
 * @Date: 2019-06-01 19:22
 * @Description: 密码加密，解密
 */
@Slf4j
public class EncryptionUtil {

    private static final String HEX_NUMS_STR = "0123456789ABCDEF";
    private static final Integer SALT_LENGTH = 12;

    /**
     * 解密密码
     *
     * @param password    初始加密密码
     * @param oldPassword 数据库密码
     * @param saltStr     加密盐
     * @return boolean
     */
    public static boolean decryption(String password, String oldPassword, String saltStr) {
        try {
            //将16进制字符串格式口令转换成字节数组
            byte[] pwdInDb = hexStringToByte(oldPassword);
            //声明盐变量
            byte[] salt = saltStr.getBytes();
            //将盐从数据库中保存的口令字节数组中提取出来
            System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH);
            //创建消息摘要对象
            MessageDigest md = MessageDigest.getInstance("SHA");
            //将盐数据传入消息摘要对象
            md.update(salt);
            //将口令的数据传给消息摘要对象
            md.update(password.getBytes("UTF-8"));
            //生成输入口令的消息摘要
            byte[] digest = md.digest();
            //声明一个保存数据库中口令消息摘要的变量
            byte[] digestInDb = new byte[pwdInDb.length - SALT_LENGTH];
            //取得数据库中口令的消息摘要
            System.arraycopy(pwdInDb, SALT_LENGTH, digestInDb, 0, digestInDb.length);
            //比较根据输入口令生成的消息摘要和数据库中消息摘要是否相同

            return Arrays.equals(digest, digestInDb);
        } catch (Exception ex) {
            log.error("解密失败", ex);
            return false;
        }
    }

    /**
     * 加密
     *
     * @param password 密码
     * @param saltStr  盐
     * @return 加密密码
     */
    public static String encryption(String password, String saltStr) {
        //声明加密后的口令数组变量
        byte[] pwd = null;
        byte[] salt = saltStr.getBytes();
        try {
            //声明消息摘要对象
            MessageDigest md = null;
            //创建消息摘要
            md = MessageDigest.getInstance("SHA");
            //将盐数据传入消息摘要对象
            md.update(salt);
            //将口令的数据传给消息摘要对象
            md.update(password.getBytes("UTF-8"));
            //获得消息摘要的字节数组
            byte[] digest = md.digest();

            //因为要在口令的字节数组中存放盐，所以加上盐的字节长度
            pwd = new byte[digest.length + SALT_LENGTH];
            //将盐的字节拷贝到生成的加密口令字节数组的前12个字节，以便在验证口令时取出盐
            System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);
            //将消息摘要拷贝到加密口令字节数组从第13个字节开始的字节
            System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);
            //将字节数组格式加密后的口令转化为16进制字符串格式的口令
            return byteToHexString(pwd);
        } catch (Exception e) {
            log.error("加密失败", e);
            return null;
        }
    }

    public static String saltCreator() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

        return format.format(new Date());
    }


    /**
     * 将16进制字符串转换成字节数组
     *
     * @param hex
     * @return
     */
    private static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4
                    | HEX_NUMS_STR.indexOf(hexChars[pos + 1]));
        }
        return result;
    }

    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    private static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    public static void main(String[] args) {
        String salt = saltCreator();
        String pass = encryption("123456", salt);
        System.out.println("pass=" + pass + "salt=" + salt);

        if (decryption("123456", pass, salt)) {
            System.out.println("nibsnsds");
        }

    }

}

