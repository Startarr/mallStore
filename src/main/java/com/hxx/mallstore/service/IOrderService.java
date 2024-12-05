package com.hxx.mallstore.service;

import com.hxx.mallstore.entity.Order;
import com.hxx.mallstore.vo.OrderVO;

import java.util.List;

public interface IOrderService {
    Order create(Integer[] cids, Integer uid, String username);

    List<OrderVO> selectAllOrders(Integer uid);
}
