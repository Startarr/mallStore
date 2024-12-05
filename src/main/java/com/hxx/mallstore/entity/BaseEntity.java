package com.hxx.mallstore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @title
 */
@Data
public class BaseEntity implements Serializable {//为了便于数据传输，实现序列化接口
    private String createdUser;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createdTime;
    private String modifiedUser;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date modifiedTime;
}
