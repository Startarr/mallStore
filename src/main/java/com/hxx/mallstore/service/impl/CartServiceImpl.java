package com.hxx.mallstore.service.impl;

import com.hxx.mallstore.entity.Cart;
import com.hxx.mallstore.entity.Product;
import com.hxx.mallstore.mapper.CartMapper;
import com.hxx.mallstore.mapper.ProductMapper;
import com.hxx.mallstore.service.ICartService;
import com.hxx.mallstore.service.ex.*;
import com.hxx.mallstore.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    /**购物车的业务层依赖于购物车的持久层以及商品的持久层*/
    @Resource
    private CartMapper cartMapper;
    @Resource
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {

        //根据参数pid和uid查询购物车中该商品是否已经存在
        Cart result = cartMapper.findByUidAndPid(uid, pid);

//        Integer cid = result.getCid();
        Date date = new Date();
        if (result == null) {
            Cart cart = new Cart();

            //封装数据：uid,pid,amount
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);//注意前端传来amount时并没有和数据库商品数量进行求和

            //查询商品数据，得到商品价格并封装
            Product product = productMapper.findById(pid);
            cart.setPrice(product.getPrice());

            //封装数据：4个日志
            cart.setCreatedUser(username);
            cart.setCreatedTime(date);
            cart.setModifiedUser(username);
            cart.setModifiedTime(date);

            Integer rows = cartMapper.insert(cart);
            if (rows != 1) {
                throw new InsertException("插入数据时出现未知异常");
            }
        } else {
            //从查询结果中取出原数量，与参数amount相加，得到新的数量
            Integer num = result.getNum() + amount;//加入购物车时只会有+不可能有-

            Integer rows = cartMapper.updateNumByCid(
                    result.getCid(),
                    num,
                    username,
                    date);
            if (rows != 1) {
                throw new InsertException("更新数据时产生未知异常");
            }
        }
    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }


    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问");
        }
        Integer num = result.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知异常");
        }
        return num;
    }
    @Override
    public Integer reduceNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问");
        }
        Integer num = result.getNum();
        if(num > 0){
            num = num - 1;
            Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());
            System.out.println("num=" + num);
            if (rows != 1) {
                throw new UpdateException("更新数据时产生未知异常");
            }
            if(num == 0){
                System.out.println("hello");
                Integer rows1 = cartMapper.delByNum(num);
            }
        }
        return num;
    }


    @Override
    public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
        if(cids != null){
            List<CartVO> list = cartMapper.findVOByCids(cids);

            //可以使用for遍历,这里玩个新的,用迭代器遍历
            Iterator<CartVO> it = list.iterator();
            while (it.hasNext()) {

                //指向的是该元素之前,所以需要next得到该元素
                CartVO cart = it.next();

                if (!cart.getUid().equals(uid)) {
                    /**
                     * 不能用list.remove(cart)
                     * 在迭代器进行遍历的时候不能使用集合的移除
                     * 方法,需要用迭代器特有的移除方法
                     */
                    it.remove();
                }
            }
            return list;
        }else {
            throw new CartNotFoundException();
        }

    }

    @Override
    public Integer delByCids(Integer[] cids) {

        if(cids != null){
            return cartMapper.delByCids(cids);
        }else {
            throw new DeleteException();
        }

    }



}
