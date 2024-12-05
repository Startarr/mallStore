package com.hxx.mallstore.util;

import org.springframework.util.DigestUtils;

/**
 * @author
 * @title
 */
public class Md5PasswordUtil {
    public static String getMD5Password(String password,String salt) {
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
