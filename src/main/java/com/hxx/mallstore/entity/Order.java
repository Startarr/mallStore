package com.hxx.mallstore.entity;

/**
 * @author
 * @title
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/** 订单数据的实体类 */
@Data
public class Order extends BaseEntity {
    private Integer oid;
    private Integer uid;
    private Long totalPrice;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date orderTime;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date payTime;
    /**
     * get,set
     * equals和hashCode
     * toString
     */
}
