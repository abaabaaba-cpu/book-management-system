package com.example.demo1.mapper;

import com.example.demo1.model.BookshelfbookKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface BookshelfbookMapper {
    int deleteByPrimaryKey(BookshelfbookKey key);

    int insert(BookshelfbookKey record);

    ArrayList<BookshelfbookKey> $selectByBookshelfid(int bookshelfid);

    int insertSelective(BookshelfbookKey record);
}