package com.hxx.mallstore.service.impl;

import com.hxx.mallstore.entity.Address;
import com.hxx.mallstore.entity.Order;
import com.hxx.mallstore.entity.OrderItem;
import com.hxx.mallstore.mapper.OrderMapper;
import com.hxx.mallstore.service.IAddressService;
import com.hxx.mallstore.service.ICartService;
import com.hxx.mallstore.service.IOrderService;
import com.hxx.mallstore.service.IUserService;
import com.hxx.mallstore.service.ex.InsertException;
import com.hxx.mallstore.vo.CartVO;
import com.hxx.mallstore.vo.OrderItemOV;
import com.hxx.mallstore.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    private OrderMapper orderMapper;


    //需要调用业务层的getByAid方法
//    @Resource
//    private IAddressService addressService;

    //需要调用业务层的getVOByCids方法
    @Resource
    private ICartService cartService;

    //需要调用业务层的getByUid方法
    private IUserService userService;

    @Override
    public Order create(Integer[] cids, Integer uid, String username) {

        //返回的列表中的对象都是即将下单的
        List<CartVO> list = cartService.getVOByCids(uid, cids);

        long totalPrice = 0L;
        for (CartVO cartVO : list) {
            totalPrice += cartVO.getRealPrice()*cartVO.getNum();

        }
//        Address address = addressService.getByAid(aid, uid);
        Order order = new Order();
        order.setUid(uid);


//        UUID oid = (UUID) UUID.randomUUID();
//        order.setOid(oid);

        //封装创建时间,支付状态和总价
        order.setOrderTime(new Date());
        order.setStatus(0);
        order.setTotalPrice(totalPrice);

        //封装四个日志
        order.setCreatedUser(username);
        order.setCreatedTime(new Date());
        order.setModifiedUser(username);
        order.setModifiedTime(new Date());
        Integer rows = orderMapper.insertOrder(order);
        if (rows != 1) {
            throw new InsertException("插入数据时产生未知的异常");
        }

        //插入数据——将某条订单的所有商品的详细数据插入
        for (CartVO cartVO : list) {
            OrderItem orderItem = new OrderItem();

            /**
             * 此时获取的oid不为空,因为在配置文件里面开启了oid主
             * 键自增,所以上面的代码执行插入时就自动将oid赋值了
             */
            orderItem.setOid(order.getOid());

            orderItem.setPid(cartVO.getPid());
            orderItem.setNum(cartVO.getNum());

            orderItem.setCreatedUser(username);
            orderItem.setCreatedTime(new Date());
            orderItem.setModifiedUser(username);
            orderItem.setModifiedTime(new Date());

            rows = orderMapper.insertOrderItem(orderItem);
            if (rows != 1) {
                throw new InsertException("插入数据时产生未知的异常");
            }

            cartService.delByCids(cids);
        }
        return order;
    }

    @Override
    public List<OrderVO> selectAllOrders(Integer uid) {
        List<OrderVO> orderVOList = orderMapper.selectOrders(uid);
        if(orderVOList != null){
            for (OrderVO orderVo: orderVOList) {
                orderVo.setOrderItemOVList(orderMapper.selectOrderItems(orderVo.getOid()));
            }
        }
        return orderVOList;
    }
}

