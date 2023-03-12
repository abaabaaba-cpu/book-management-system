package com.example.demo1.web;

import com.example.demo1.mapper.TokentableMapper;
import com.example.demo1.mapper.UserMapper;
import com.example.demo1.model.Tokentable;
import com.example.demo1.model.User;
import com.example.demo1.utils.TokenProccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
public class OperatorController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private User user;
    @Autowired
    private TokentableMapper tokentableMapper;
    @Autowired
    private Tokentable tokentable;

    @PostMapping(value = "/operator/getOperator")
    public @ResponseBody
    Object GetPersonalInfo(HttpServletRequest req, HttpServletResponse rep) {
        String id = req.getParameter("id");
        user = userMapper.selectByPrimaryKey(Integer.valueOf(id));
        return user;
    }
}
