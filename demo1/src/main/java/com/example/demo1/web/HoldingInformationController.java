package com.example.demo1.web;

import com.example.demo1.mapper.*;
import com.example.demo1.model.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class HoldingInformationController {
    @Data
    private class HoldingInfo{
        int bookid;
        int holdingnum;
        int remain;
    }
    @Autowired
    private HoldingInformationMapper holdingInformationMapper;
    @PostMapping(value = "/holdingInformation/getHoldingInformationByBookid")
    public @ResponseBody
    List<HoldingInformation> getHoldingInformationByBookid(HttpServletRequest req, HttpServletResponse rep) {
        String bookid = req.getParameter("bookid");
        ArrayList<HoldingInformation> holdingInformations = new ArrayList<>();
        holdingInformations = holdingInformationMapper.$selectByBookid(Integer.valueOf(bookid));
        return holdingInformations;
    }
    @PostMapping(value = "/holdingInformation/getBooknumByBookid")
    public @ResponseBody
    String getBooknumByBookid(HttpServletRequest req, HttpServletResponse rep) {
        String bookid = req.getParameter("bookid");
        ArrayList<HoldingInformation> holdingInformations = new ArrayList<>();
        holdingInformations = holdingInformationMapper.$selectByBookid(Integer.valueOf(bookid));
        return holdingInformations.get(0).getBooknum();
    }
    @PostMapping(value = "/holdingInformation/getHoldingnumAndRemainByBookid")
    public @ResponseBody
    ArrayList<HoldingInfo> getHoldingnumAndRemainByBookid(HttpServletRequest req, HttpServletResponse rep) {
        ArrayList<HoldingInfo> holdingInfos= new ArrayList<>();
        String length = req.getParameter("length");
        int[] idList = new int[Integer.valueOf(length)];
        for(int i = 0;i < Integer.valueOf(length);i++)
            idList[i] = Integer.valueOf(req.getParameter(String.format("bookid[%d]",i)));
        ArrayList<HoldingInformation> holdingInformations = new ArrayList<>();
        for(int i = 0;i < idList.length;i++)
        {
            holdingInformations = holdingInformationMapper.$selectByBookid(idList[i]);
            int holdingnum = holdingInformations.size();
            int remain = 0;
            for(Iterator j = holdingInformations.iterator();j.hasNext();)
            {
                int state = ((HoldingInformation)(j.next())).getState();
                if(state == 1)
                    remain++;
            }
            HoldingInfo holdingInfo = new HoldingInfo();
            holdingInfo.setHoldingnum(holdingnum);
            holdingInfo.setBookid(idList[i]);
            holdingInfo.setRemain(remain);
            holdingInfos.add(holdingInfo);
        }
        return holdingInfos;
    }
    @PostMapping(value = "/holdingInformation/getBookByBarcode")
    public @ResponseBody
    HoldingInformation getBookByBarcode(HttpServletRequest req, HttpServletResponse rep) {
        String barcode = req.getParameter("barcode");
        HoldingInformation holdingInformation = holdingInformationMapper.selectByPrimaryKey(barcode);
        if(holdingInformation != null){
            return holdingInformation;
        }
        else
            return null;
    }
    @PostMapping(value = "/holdingInformation/getHoldingnumByBookid")
    public @ResponseBody
    int getHoldingnumByBookid(HttpServletRequest req, HttpServletResponse rep) {
        int bookid = Integer.valueOf(req.getParameter("bookid"));
        ArrayList<HoldingInformation> holdingInformations = new ArrayList<>();
        holdingInformations = holdingInformationMapper.$selectByBookid(bookid);
        return holdingInformations.size();
    }
    @PostMapping(value = "/holdingInformation/editHoldingInfo")
    public @ResponseBody
    boolean editHoldingInfo(HttpServletRequest req, HttpServletResponse rep) {
        String oldBarcode = req.getParameter("barcode");
        String newBarcode = req.getParameter("newBarcode");
        int bookid = Integer.valueOf(req.getParameter("bookid"));
        String position = req.getParameter("position");
        String booknum = req.getParameter("booknum");
        int state = Integer.valueOf(req.getParameter("state"));
        HoldingInformation holdingInformation = new HoldingInformation();
        holdingInformation.setBarcode(newBarcode);
        holdingInformation.setState(state);
        holdingInformation.setBookid(bookid);
        holdingInformation.setPosition(position);
        holdingInformation.setBooknum(booknum);
       //新条码号不能为空
        if(newBarcode.equals("") == false){
            //旧条码号为空表示新增馆藏信息
            if(oldBarcode != null) {
                //若新旧条码号相同则仅需更新记录，若不同则需删除后插入
                if (oldBarcode.equals(newBarcode) == false) {
                    if (holdingInformationMapper.selectByPrimaryKey(newBarcode) != null) {
                        rep.setStatus(202);
                        return false;
                    }
                    holdingInformationMapper.deleteByPrimaryKey(oldBarcode);
                    holdingInformationMapper.insert(holdingInformation);
                    rep.setStatus(200);
                    return true;
                }
                else {
                    holdingInformationMapper.updateByPrimaryKeySelective(holdingInformation);
                    rep.setStatus(200);
                    return true;
                }
            }
            else{
                if(holdingInformationMapper.selectByPrimaryKey(newBarcode) != null){
                    rep.setStatus(202);
                    return false;
                }
                holdingInformationMapper.insertSelective(holdingInformation);
                rep.setStatus(200);
                return true;
            }
        }
        else{
            rep.setStatus(201);
            return false;
        }
    }
}
