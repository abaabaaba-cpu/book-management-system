package com.example.demo1.mapper;

import com.example.demo1.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    ArrayList<User> $selectStudentsOrOps(Integer permission);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}