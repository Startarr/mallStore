package com.hxx.mallstore.entity;

import lombok.Data;

/**收货地址额实体类*/
@Data
public class Address extends BaseEntity {
    private Integer aid;
    private Integer uid;
    private String name;
    private String provinceName;
    private String provinceCode;
    private String cityName;
    private String cityCode;
    private String areaName;
    private String areaCode;
    private String zip;
    private String address;
    private String phone;
    private String tel;
    private String tag;
    private Integer isDefault;
    /**
     * get,set
     * equals和hashCode
     * toString
     */
}

