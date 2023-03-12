package com.example.demo1.mapper;

import com.example.demo1.model.Book;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Mapper
public interface BookMapper {
    int deleteByPrimaryKey(Integer bookid);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(Integer bookid);

    ArrayList<Book> selectAll();

    ArrayList<Book> $selectByCategory(String category);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);
}