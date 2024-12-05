package com.hxx.mallstore.service;

import com.github.pagehelper.PageInfo;
import com.hxx.mallstore.entity.Favorites;

public interface IFavoritesService {
    String addFavorites(Integer uid,Integer pid);

    PageInfo favoritesList(Integer uid,Integer pageNum);
}
