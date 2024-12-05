package com.hxx.mallstore.controller;

import com.hxx.mallstore.service.ex.*;
import com.hxx.mallstore.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * @author
 * @title
 */
//全局处理异常
public class BaseController {
    //操作成功的状态码
    public static final int OK = 200;

    /**
     * 1.当出现了value内的异常之一，就会将下方的方法作为新的控制器方法进行执行
     *   因此该方法的返回值也同时是返回给前端的页面
     * 2.此外还自动将异常对象传递到此方法的参数列表中，这里使用Throwable e来接收
     **/
    @ExceptionHandler(ServiceException.class) //统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicateException){
            result.setState(4000); //表示用户名重复
            result.setMessage(e.getMessage());
        } else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
            result.setMessage("用户的收货地址超出上限的异常");
        }else if (e instanceof AddressNotFoundException) {
            result.setState(4004);
            result.setMessage("用户的收货地址数据不存在的异常");
        } else if (e instanceof AccessDeniedException) {
            result.setState(4005);
            result.setMessage("收货地址数据非法访问的异常");
        }else if (e instanceof ProductNotFoundException) {
            result.setState(4006);
            result.setMessage("访问的商品数据不存在的异常");
        }else if (e instanceof CartNotFoundException) {
            result.setState(4007);
            result.setMessage("购物车表不存在该商品的异常");
        } else if (e instanceof InsertException){
            result.setState(5000); //数据库或服务器有问题
            result.setMessage(e.getMessage());
        }else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage("更新数据时产生未知的异常");
        }else if (e instanceof DeleteException) {
            result.setState(5002);
            result.setMessage("删除数据时产生未知的异常");
        }else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        }

        //返回异常处理结果
        return result;
    }

    /**
     * 获取session对象中的uid
     * @param session session对象
     * @return 当前登录的用户uid的值
     */
    public final Integer getUidFromSession(HttpSession session) {
        //getAttribute返回的是Object对象,需要转换为字符串再转换为包装类
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    public final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }

}