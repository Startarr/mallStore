package com.hxx.mallstore.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author
 * @title
 */
@Data
public class OrderItemOV implements Serializable {
    private Long price;
    private Integer num;
    private String title;
    private String image;
}
