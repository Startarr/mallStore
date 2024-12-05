package com.hxx.mallstore.mapper;

import com.hxx.mallstore.entity.Order;
import com.hxx.mallstore.entity.OrderItem;
import com.hxx.mallstore.vo.OrderItemOV;
import com.hxx.mallstore.vo.OrderVO;

import java.util.List;

/**
 * @author
 * @title
 */
public interface OrderMapper {
    /**
     * 插入订单数据
     * @param order 订单数据
     * @return 受影响的行数
     */
    Integer insertOrder(Order order);

    /**
     * 插入某一个订单中商品数据
     * @param orderItem 订单中商品数据
     * @return 受影响的行数
     */
    Integer insertOrderItem(OrderItem orderItem);


    List<OrderVO> selectOrders(Integer uid);

    List<OrderItemOV> selectOrderItems(Integer oid);
}

