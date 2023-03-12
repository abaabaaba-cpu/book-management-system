package com.example.demo1.mapper;

import com.example.demo1.model.Bookshelfuser;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
@Mapper
public interface BookshelfuserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bookshelfuser record);

    int insertSelective(Bookshelfuser record);

    Bookshelfuser selectByPrimaryKey(Integer id);

    ArrayList<Bookshelfuser> $selectByStudentid(Integer studentid);

    int updateByPrimaryKeySelective(Bookshelfuser record);

    int updateByPrimaryKey(Bookshelfuser record);
}