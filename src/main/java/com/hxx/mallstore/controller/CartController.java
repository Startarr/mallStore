package com.hxx.mallstore.controller;

import com.hxx.mallstore.service.ICartService;
import com.hxx.mallstore.util.JsonResult;
import com.hxx.mallstore.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController {
    @Autowired
    private ICartService cartService;

    @RequestMapping("add_to_cart")
    public JsonResult addToCart(Integer pid, Integer amount, HttpSession session) {
        cartService.addToCart(
                getUidFromSession(session),
                pid,
                amount,
                getUsernameFromSession(session));
        return new JsonResult<Void>(OK);
    }

    @RequestMapping("{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer data = cartService.addNum(
                cid,
                getUidFromSession(session),
                getUsernameFromSession(session));
        return new JsonResult<Integer>(OK, data);
    }

    @RequestMapping("{cid}/num/reduceNum")
    public JsonResult<Integer> reduceNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer data = cartService.reduceNum(
                cid,
                getUidFromSession(session),
                getUsernameFromSession(session));
        return new JsonResult<Integer>(OK, data);
    }

    @RequestMapping("list")
    public JsonResult<List<CartVO>> findVOByCids(Integer[] cids, HttpSession session) {
        List<CartVO> data = cartService.getVOByCids(getUidFromSession(session), cids);
        return new JsonResult<>(OK, data);
    }

    @RequestMapping({"", "/"})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        List<CartVO> data = cartService.getVOByUid(getUidFromSession(session));
        return new JsonResult<List<CartVO>>(OK, data);
    }

    @RequestMapping("delBycids")
    public JsonResult delByCids(Integer[] cids){
        Integer row = cartService.delByCids(cids);
        return new JsonResult(OK);
    }
}

