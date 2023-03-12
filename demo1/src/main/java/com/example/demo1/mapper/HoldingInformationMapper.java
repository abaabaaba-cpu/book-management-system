package com.example.demo1.mapper;

import com.example.demo1.model.HoldingInformation;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
@Mapper
public interface HoldingInformationMapper {
    int deleteByPrimaryKey(String barcode);

    int insert(HoldingInformation record);

    int insertSelective(HoldingInformation record);

    HoldingInformation selectByPrimaryKey(String barcode);

    ArrayList<HoldingInformation> $selectByBookid(Integer bookid);

    int updateByPrimaryKeySelective(HoldingInformation record);

    int updateByPrimaryKey(HoldingInformation record);
}