package com.hxx.mallstore.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxx.mallstore.entity.Product;
import com.hxx.mallstore.mapper.ProductMapper;
import com.hxx.mallstore.service.IProductService;
import com.hxx.mallstore.service.ex.ProductNotFoundException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @title
 */
@Service
public class ProductServiceImpl implements IProductService {
    @Resource
    private ProductMapper productMapper;

    @Override
    public List<Product> queryRecentProduct() {
        return productMapper.queryRecentProduct();
    }

    @Override
    public List<Product> queryHotProduct() {
        return productMapper.queryHotProduct();
    }

    @Override
    public Product findById(Integer id) {
        Product product = productMapper.findById(id);
        // 判断查询结果是否为null
        if (product == null) {
            throw new ProductNotFoundException("尝试访问的商品数据不存在");
        }
        // 将查询结果中的部分属性设置为null
        product.setPriority(null);
        product.setCreatedUser(null);
        product.setCreatedTime(null);
        product.setModifiedUser(null);
        product.setModifiedTime(null);

        return product;
    }

    @Override
    public List<Product> queryPageProduct(Integer page, String type) {
        page = (page - 1) * 8;
        return productMapper.queryPageProduct(type);
    }

    @Override
    public PageInfo<Product> queryPage(Integer page, String type,Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Product> list = productMapper.queryPageProduct(type);
        PageInfo<Product> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
