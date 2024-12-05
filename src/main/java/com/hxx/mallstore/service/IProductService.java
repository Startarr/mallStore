package com.hxx.mallstore.service;

import com.github.pagehelper.PageInfo;
import com.hxx.mallstore.entity.Product;

import java.util.List;

public interface IProductService {

    //查询最近上架的商品
    List<Product> queryRecentProduct();

    //查询热销商品的前五项的抽象方法
    List<Product> queryHotProduct();

    /**
     * 根据商品id查询商品详情
     * @param id 商品id
     * @return 匹配的商品详情，如果没有匹配的数据则返回null
     */
    Product findById(Integer id);

    List<Product> queryPageProduct(Integer page, String type);

    PageInfo<Product> queryPage(Integer page, String type, Integer pageSize);

}
