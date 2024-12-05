package com.hxx.mallstore.mapper;

import com.hxx.mallstore.entity.Favorites;

import java.util.List;

public interface FavoritesMapper {
    //新增收藏商品的抽象方法
    Integer addFavorites(Integer uid, Integer pid);

    Integer findByUidAndPid(Integer uid,Integer pid);

    Integer deleteByFid(Integer fid);

    List<Favorites> findByUid(Integer uid);
}
