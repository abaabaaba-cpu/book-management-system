package com.example.demo1.web;

import com.example.demo1.mapper.*;
import com.example.demo1.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class BorrowHistoryController {
    @Autowired
    private HoldingInformationMapper holdingInformationMapper;
    @Autowired
    private BorrowHistory borrowHistory;
    @Autowired
    private BorrowHistoryMapper borrowHistoryMapper;
    @Autowired
    private UserMapper userMapper;
    @PostMapping(value = "/borrowHistory/getBorrowHistory")
    public @ResponseBody
    List<BorrowHistory> getBorrowHistory(HttpServletRequest req, HttpServletResponse rep) {
        String studentId = req.getParameter("id");
        ArrayList<BorrowHistory> borrowHistories = new ArrayList<>();
        borrowHistories = borrowHistoryMapper.$selectByStudentId(Integer.valueOf(studentId));
        for(Iterator i = borrowHistories.iterator();i.hasNext();)
        {
            borrowHistory = (BorrowHistory) (i.next());
            if(borrowHistory.getReturntime() == null)
            {
                i.remove();
            }
        }
        return borrowHistories;
    }
    @PostMapping(value = "/borrowHistory/getAll")
    public @ResponseBody
    List<BorrowHistory> getAll(HttpServletRequest req, HttpServletResponse rep) {
        ArrayList<BorrowHistory> borrowHistories = new ArrayList<>();
        borrowHistories = borrowHistoryMapper.$selectAll();
        return borrowHistories;
    }
    @PostMapping(value = "/borrowHistory/getCurrentBorrowingByUserid")
    public @ResponseBody
    List<BorrowHistory> getCurrentBorrowingByUserid(HttpServletRequest req, HttpServletResponse rep) {
        ArrayList<BorrowHistory> borrowHistories = new ArrayList<>();
        int userid = Integer.valueOf(req.getParameter("userid"));
        borrowHistories = borrowHistoryMapper.$selectByStudentId(userid);
        for(Iterator i = borrowHistories.iterator();i.hasNext();)
        {
            borrowHistory = (BorrowHistory) (i.next());
            if(borrowHistory.getReturntime() != null)
            {
                i.remove();
            }
        }
        return borrowHistories;
    }
    @PostMapping(value = "/borrowHistory/getBookidByBarcode")
    public @ResponseBody
    int getBookidByBarcode(HttpServletRequest req, HttpServletResponse rep) {
        String barcode = req.getParameter("barcode");
        int bookid = (holdingInformationMapper.selectByPrimaryKey(barcode)).getBookid();
        return bookid;
    }
    @PostMapping(value = "/borrowHistory/changeLossState")
    public @ResponseBody
    int changeLossState(HttpServletRequest req, HttpServletResponse rep) {
        int borrowid = Integer.valueOf(req.getParameter("borrowid"));
        borrowHistory = borrowHistoryMapper.selectByPrimaryKey(borrowid);
        if(borrowHistory.getLoss() == 1) {
            borrowHistory.setLoss(0);
        }
        else{
            borrowHistory.setLoss(1);
        }
        borrowHistoryMapper.updateByPrimaryKeySelective(borrowHistory);
        return 1;
    }
    @PostMapping(value = "/borrowHistory/deleteBorrowHistory")
    public @ResponseBody
    boolean deleteBorrowHsitory(HttpServletRequest req, HttpServletResponse rep) {
        int borrowid = Integer.valueOf(req.getParameter("borrowid"));
        if(borrowHistoryMapper.deleteByPrimaryKey(borrowid) != 0)
        {
            return true;
        }
        return false;
    }
    @PostMapping(value = "/borrowHistory/returnBook")
    public @ResponseBody
    boolean returnBook(HttpServletRequest req, HttpServletResponse rep) {
        int borrowid = Integer.valueOf(req.getParameter("borrowid"));
        String barcode = req.getParameter("barcode");
        borrowHistory.setBorrowid(borrowid);
        Date now = new Date();
        borrowHistory.setReturntime(now);
        borrowHistoryMapper.updateByPrimaryKeySelective(borrowHistory);
        HoldingInformation holdingInformation = new HoldingInformation();
        holdingInformation.setBarcode(barcode);
        holdingInformation.setState(1);
        holdingInformationMapper.updateByPrimaryKeySelective(holdingInformation);
        return true;
    }
    @PostMapping(value = "/borrowHistory/pay")
    public @ResponseBody
    boolean pay(HttpServletRequest req, HttpServletResponse rep) {
        int borrowid = Integer.valueOf(req.getParameter("borrowid"));
        borrowHistory.setBorrowid(borrowid);
        Date now = new Date();
        borrowHistory.setReturntime(now);
        borrowHistory.setLoss(0);
        borrowHistoryMapper.updateByPrimaryKeySelective(borrowHistory);
        return true;
    }
    @PostMapping(value = "/borrowHistory/borrowBooks")
    public @ResponseBody
    ArrayList<String> borrowBooks(HttpServletRequest req, HttpServletResponse rep) {
        int studentid = Integer.valueOf(req.getParameter("studentid"));
        int length = Integer.valueOf(req.getParameter("length"));
        ArrayList<String> successBarcodeList = new ArrayList<>();
        boolean allowBorrow = true;
        ArrayList<String> barcodeList = new ArrayList<>();
        HoldingInformation holdingInformation = new HoldingInformation();
        User user = new User();
        user = userMapper.selectByPrimaryKey(studentid);
        ArrayList<BorrowHistory> borrowHistories = new ArrayList<>();
        borrowHistories = borrowHistoryMapper.$selectByStudentId(studentid);
        //移除已完成的借阅记录
        for(Iterator<BorrowHistory> iterator = borrowHistories.iterator();iterator.hasNext();){
            if(iterator.next().getReturntime() != null)
                iterator.remove();
        }
        //判断用户是否有权借阅
        if(user.getMaxborrowing() > borrowHistories.size()) {
            for (Iterator<BorrowHistory> iterator = borrowHistories.iterator(); iterator.hasNext(); ) {
                BorrowHistory temp = iterator.next();
                Date now = new Date();
                if(temp.getDuedate().compareTo(now) > 0) {
                    if (temp.getLoss() == 1) {
                        allowBorrow = false;
                    }
                }
                else {
                    allowBorrow = false;
                }
            }
        }
        else {
            allowBorrow = false;
        }
        for(int i = 0;i < length;i++){
            barcodeList.add(req.getParameter(String.format("barcodeList[%d]",i)));
        }
        //若用户有权借阅则开始借阅流程
        if(allowBorrow == true) {
            for (Iterator iterator = barcodeList.iterator(); iterator.hasNext(); ) {
                holdingInformation.setBarcode((String) iterator.next());
                //判断该书籍是否可借
                if (holdingInformationMapper.selectByPrimaryKey(holdingInformation.getBarcode()).getState() == 1) {
                    Date borrowtime = new Date();
                    Date duedate = new Date(borrowtime.getTime() + (long) 30 * 24 * 60 * 60 * 1000);
                    BorrowHistory borrowHistory = new BorrowHistory();
                    borrowHistory.setBorrowtime(borrowtime);
                    borrowHistory.setDuedate(duedate);
                    borrowHistory.setStudentid(studentid);
                    borrowHistory.setBarcode(holdingInformation.getBarcode());
                    borrowHistory.setLoss(0);
                    borrowHistoryMapper.insertSelective(borrowHistory);
                    holdingInformation.setState(0);
                    holdingInformationMapper.updateByPrimaryKeySelective(holdingInformation);
                    successBarcodeList.add(holdingInformation.getBarcode());
                }
            }
        }
        return successBarcodeList;
    }
}
