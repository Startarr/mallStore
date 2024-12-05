package com.hxx.mallstore.controller;

import com.github.pagehelper.PageInfo;
import com.hxx.mallstore.entity.Favorites;
import com.hxx.mallstore.service.IFavoritesService;
import com.hxx.mallstore.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author
 * @title
 */
@RequestMapping("favorites")
@RestController
public class FavoritesController extends BaseController{
    @Resource
    IFavoritesService favoritesService;

    @PostMapping("addFavorites")
    public JsonResult<String> addFavorites(HttpSession session, Integer pid){
        //从session中取出uid
        Integer uid = getUidFromSession(session);
        //执行插入操作并返回fid
        String data = favoritesService.addFavorites(uid,pid);
        return new JsonResult<>(OK,data);
    }

    @GetMapping("favoritesList/{pageNum}")
    public JsonResult<PageInfo<Favorites>> getFavoritesList (HttpSession session, @PathVariable("pageNum")Integer pageNum){
        Integer uid = getUidFromSession(session);
        PageInfo<Favorites> data = favoritesService.favoritesList(uid,pageNum);
        return new JsonResult<>(OK,data);
    }
}
