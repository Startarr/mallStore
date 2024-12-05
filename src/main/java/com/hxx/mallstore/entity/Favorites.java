package com.hxx.mallstore.entity;

import lombok.Data;

/**
 * @author
 * @title
 */
//对应数据表t_favorites的实体类
@Data
public class Favorites{
    private Integer fid;
    private Integer uid;
    private Integer pid;
    private String image;
    private Long price;
    private String title;
    private String sellPoint;
}