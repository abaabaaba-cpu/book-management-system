package com.example.demo1.model;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;
@Data
@Component
public class Tokentable {
    private Integer id;

    private String token;

    private Date starttime;

    private String url;
}