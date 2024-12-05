package com.hxx.mallstore.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxx.mallstore.entity.Favorites;
import com.hxx.mallstore.entity.Product;
import com.hxx.mallstore.mapper.FavoritesMapper;
import com.hxx.mallstore.service.IFavoritesService;
import com.hxx.mallstore.service.IProductService;
import com.hxx.mallstore.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @title
 */
@Service
public class FavoritesServiceImpl implements IFavoritesService {
    @Autowired
    IProductService productService;
    @Resource
    FavoritesMapper favoritesMapper;

    @Override
    public String addFavorites(Integer uid, Integer pid) {
        String result;
        Integer fid = favoritesMapper.findByUidAndPid(uid, pid);
        if (fid == null) {
            Integer row = favoritesMapper.addFavorites(uid, pid);
            if (row != 1) {
                throw new InsertException("收藏失败！");
            }else {
                result = "收藏成功";
            }
        } else {
            Integer row = favoritesMapper.deleteByFid(fid);
            if (row != 1) {
                throw new InsertException("移除收藏失败！");
            }else {
                result = "移除收藏成功";
            }
        }
        return result;
    }

    @Override
    public PageInfo favoritesList(Integer uid,Integer pageNum) {
        PageHelper.startPage(pageNum,8);
        List<Favorites> favoritesList =  favoritesMapper.findByUid(uid);
        PageInfo<Favorites> pageInfo = new PageInfo<>(favoritesList);
        return pageInfo;
    }
}
