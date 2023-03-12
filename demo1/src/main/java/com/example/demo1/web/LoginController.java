package com.example.demo1.web;

import com.example.demo1.mapper.TokentableMapper;
import com.example.demo1.mapper.UserMapper;
import com.example.demo1.model.Tokentable;
import com.example.demo1.model.User;
import com.example.demo1.utils.TokenProccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
public class LoginController {
    @RequestMapping(value = "/login")
    public String index(){
        return "login";
    }
}
