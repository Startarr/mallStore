package com.hxx.mallstore.entity;

import lombok.Data;

/**购物车数据的实体类*/
@Data
public class Cart extends BaseEntity {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
/**
 * get,set
 * equals和hashCode
 * toString
 */
}
