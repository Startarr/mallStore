package com.hxx.mallstore.mapper;

import com.hxx.mallstore.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    //查询优先权最近上架前五的商品进行展示
    List<Product> queryRecentProduct();

    //查询优先权销售最多前五的商品进行展示
    List<Product> queryHotProduct();

    //分页查询全部商品
//    List<Product> queryPageProduct(Integer page,String type);
    List<Product> queryPageProduct(String type);

    /**
     * 根据商品id查询商品详情
     * @param id 商品id
     * @return 匹配的商品详情，如果没有匹配的数据则返回null
     */
    Product findById(Integer id);
}
