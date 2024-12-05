package com.hxx.mallstore.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @author
 * @title
 */
//响应数据给前端
@Data
public class JsonResult<E> {
    //验证码错误
    public static final int CODE_ERR_BUSINESS = 501;
    //用户未登录
    public static final int CODE_ERR_UNLOGINED = 502;
    //响应状态码 200-成功 4000-用户名重复 5000-数据库或服务器异常
    private int state;
    //响应信息
    private String message;
    //响应数据
    private E data;

    public JsonResult() {
    }

    public JsonResult(int state) {
        this.state = state;
    }

    public JsonResult(Throwable e) {
        this.message = e.getMessage();
    }

    public JsonResult(int state, E data) {
        this.state = state;
        this.data = data;
    }

}

