package com.example.demo1.mapper;

import com.example.demo1.model.Tokentable;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokentableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tokentable record);

    int insertSelective(Tokentable record);

    Tokentable selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tokentable record);

    int updateByPrimaryKey(Tokentable record);
}