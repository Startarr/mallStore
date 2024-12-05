package com.hxx.mallstore.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxx.mallstore.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @title
 */
@Data
public class OrderVO extends BaseEntity implements Serializable {
    private Integer oid;
    private Integer uid;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date OrderTime;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date payTime;
    private Long totalPrice;
    private List<OrderItemOV> orderItemOVList;
}
