package com.hxx.mallstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//对应数据表t_product的实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity{
    private Integer id;
    private Integer categoryId ;
    private String itemType;
    private String title;
    private String sellPoint;
    private Long price;
    private Integer num;
    private String image;
    private Integer status;
    private String priority;
}
