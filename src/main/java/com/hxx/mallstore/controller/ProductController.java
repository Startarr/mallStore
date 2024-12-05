package com.hxx.mallstore.controller;

import com.github.pagehelper.PageInfo;
import com.hxx.mallstore.entity.Product;
import com.hxx.mallstore.service.ICartService;
import com.hxx.mallstore.service.IProductService;
import com.hxx.mallstore.util.JsonResult;
import com.hxx.mallstore.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController extends BaseController {
    @Autowired
    private IProductService productService;

    @Resource
    private ICartService cartService;

    @RequestMapping("recent_list")
    public JsonResult<List<Product>> getRecentList() {
        List<Product> data = productService.queryRecentProduct();
        return new JsonResult<List<Product>>(OK, data);
    }

    @RequestMapping("hot_list")
    public JsonResult<List<Product>> getHotList() {
        List<Product> data = productService.queryHotProduct();
        return new JsonResult<List<Product>>(OK, data);
    }

//    @RequestMapping("product_list/{page}/{type}")
//    public JsonResult<List<Product>> getPageProductList(@PathVariable("page") Integer page, @PathVariable("type") String type) {
//        List<Product> data = productService.queryPageProduct(page,type);
//        return new JsonResult<List<Product>>(OK, data);
//    }

    @RequestMapping("product_list/{page}/{type}")
    public JsonResult<PageInfo<Product>> getPageProductPageInfo(@PathVariable("page") Integer page, @PathVariable("type") String type,Integer pageSize) {
        PageInfo<Product> data = productService.queryPage(page,type,8);
        return new JsonResult<PageInfo<Product>>(OK, data);
    }

    @GetMapping("{id}/details")
    public JsonResult<Product> getById(@PathVariable("id") Integer id) {
        Product data = productService.findById(id);
        return new JsonResult<Product>(OK, data);
    }

    @RequestMapping
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        List<CartVO> data = cartService.getVOByUid(getUidFromSession(session));
        return new JsonResult<List<CartVO>>(OK, data);
    }
}