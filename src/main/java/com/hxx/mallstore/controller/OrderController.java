package com.hxx.mallstore.controller;

import com.hxx.mallstore.entity.Order;
import com.hxx.mallstore.service.IOrderService;
import com.hxx.mallstore.util.JsonResult;
import com.hxx.mallstore.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {
    @Resource
    private IOrderService orderService;

    @RequestMapping("create")
    public JsonResult<Order> create(Integer[] cids, HttpSession session) {
        Order data = orderService.create(
                cids,
                getUidFromSession(session),
                getUsernameFromSession(session));
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("all")
    public JsonResult<List<OrderVO>> selectAllOrders(HttpSession session) {
        List<OrderVO> data = orderService.selectAllOrders(getUidFromSession(session));
        return new JsonResult<>(OK,data);
    }
}

