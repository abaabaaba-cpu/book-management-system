package com.example.demo1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Book {
    private Integer bookid;

    private String bookname;

    private Integer remain;

    private String author;

    private String press;

    private Date publicationdate;

    private Integer version;

    private String language;

    private String category;

    private String introduction;

    private String clcnum;


}