package com.example.demo1.mapper;

import com.example.demo1.model.BorrowHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
@Mapper
public interface BorrowHistoryMapper {
    int deleteByPrimaryKey(Integer borrowid);

    int insert(BorrowHistory record);

    int insertSelective(BorrowHistory record);

    BorrowHistory selectByPrimaryKey(Integer borrowid);

    ArrayList<BorrowHistory> $selectByStudentId(Integer studentId);

    ArrayList<BorrowHistory> $selectAll();

    int updateByPrimaryKeySelective(BorrowHistory record);

    int updateByPrimaryKey(BorrowHistory record);
}