package com.hxx.mallstore.service.impl;

import com.hxx.mallstore.entity.User;
import com.hxx.mallstore.mapper.UserMapper;
import com.hxx.mallstore.service.IUserService;
import com.hxx.mallstore.service.ex.*;
import com.hxx.mallstore.util.Md5PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.Year;
import java.util.Date;
import java.util.UUID;

/**
 * @author
 * @title
 */
@Service
public class IUserSreviceImpl implements IUserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public void UserRegister(User user) {
        //通过user参数来获取传递过来的username
        String username = user.getUsername();
        //调用mapper的findByUsername(username)判断用户是否被注册过了
        User result = userMapper.queryUserByUsername(username);
        //判断结果集是否为null,不为null的话则需抛出用户名被占用的异常
        if (result != null) {
            //抛出异常
            throw new UsernameDuplicateException("用户名被占用");
        }

        /**
         * 密码加密处理作用:
         * 1.后端不再能直接看到用户的密码2.忽略了密码原来的强度,提升了数据安全性
         * 密码加密处理的实现:
         * 串+password+串->交给md5算法连续加密三次
         * 串就是数据库字段中的盐值,是一个随机字符串
         */
        String oldpassword = user.getPassword();
        //1.随机生成一个盐值(大写的随机字符串)
        String salt = UUID.randomUUID().toString().toUpperCase();
        //2.将密码和盐值作为一个整体进行加密处理
        String md5Password = Md5PasswordUtil.getMD5Password(oldpassword, salt);
        //3.将盐值保存到数据库
        user.setSalt(salt);
        //4.将加密之后的密码重新补全设置到user对象当中
        user.setPassword(md5Password);

        //补全数据:is_delete设置为0
        user.setIsDelete(0);
        //补全数据:四个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();//java.util.Date
        user.setCreatedTime(date);
        user.setModifiedTime(date);


        //执行注册业务功能的实现
        Integer rows = userMapper.addUser(user);
        if (rows != 1) {
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }

    }

    @Override
    public User userLogin(User user) {
        String username = user.getUsername();
        //用户输入的密码
        String userPassword = user.getPassword();

        //查询登录用户是否在数据库中存在
        User loginUser = userMapper.queryUserByUsername(username);

        if (loginUser == null){ //为空代表用户名不存在
            throw new UsernameNotFoundException("用户数据不存在");
        }

        //取得数据库查询返回用户的盐值和密码以及删除状态
        String salt = loginUser.getSalt();
        String databasePwd = loginUser.getPassword();
        Integer deleteStatus = loginUser.getIsDelete();

        //对用户输入的密码进行加密
        String md5PasswordBy = Md5PasswordUtil.getMD5Password(userPassword, salt);

        //将加密后的字符和数据库查询的MD5进行校验
        if (!databasePwd.equals(md5PasswordBy)){
            throw new PasswordNotMatchException("密码不正确");
        }

        //判断登录的用户账户是否已注销
        if (deleteStatus == 1){
            throw new UsernameNotFoundException("用户数据不存在");
        }

        //密码正确返回查询的用户信息
        return loginUser;
    }

    @Override
    public void changePassword(Integer uid,
                               String username,
                               String oldPassword,
                               String newPassword) {
        User result = userMapper.findByUid(uid);
        /**
         * 用户没找到:比如登录账号后的几分钟在和朋友聊天,没
         * 有看页面,管理员错误删除了你的账号或者错误设置is_delete为1)
         */
        if (result ==null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户数据不存在");
        }

        //原始密码和数据库中密码进行比较
        String oldMd5Password = Md5PasswordUtil.getMD5Password(oldPassword,result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)) {
            throw new PasswordNotMatchException("密码错误");
        }

        //将新的密码加密后设置到数据库中(只要曾经注册过就用以前的盐值)
        String newMd5Password = Md5PasswordUtil.getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());

        if (rows != 1) {
            throw new UpdateException("更新数据产生未知的异常");
        }
    }


    @Override
    public User getByUid(Integer uid) {
        //查询用户是否存在
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户数据不存在");
        }

        //可以直接返回result给控制层,但是太臃肿了
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        return user;
    }

    /**
     *User对象中的数据只有phone,email,gender,username,因为springboot进行依赖
     * 注入的时候只注入表单中数据的值,所以需要手动将uid封装到user中
     */
    @Override
    public void changeInfo(Integer uid, User user) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(user.getUsername());
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateInfoByUid(user);
        if (rows!=1) {
            throw new UpdateException("更新数据时产生异常");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        //查询当前的用户数据是否存在
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户数据不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (rows!=1) {
            throw new UpdateException("更新用户头像时产生未知异常");
        }
    }

}
