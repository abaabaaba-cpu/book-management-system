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
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private User user;
    @Autowired
    private TokentableMapper tokentableMapper;
    @Autowired
    private Tokentable tokentable;

    @PostMapping(value = "/userLogin")
    public @ResponseBody
    Object operatorLogin(HttpServletRequest req, HttpServletResponse rep) {
        String password = req.getParameter("password");
        String id = req.getParameter("id");
        user = userMapper.selectByPrimaryKey(Integer.valueOf(id));
        if (user == null) {
            rep.setStatus(201);
            return null;
        } else {
            if (user.getPassword().equals(password)) {
                TokenProccessor tokenProccessor = new TokenProccessor();
                String token = tokenProccessor.makeToken();
                tokentable.setId(Integer.valueOf(id));
                tokentable.setToken(token);
                tokentable.setStarttime(new Date());
                if (tokentableMapper.selectByPrimaryKey(Integer.valueOf(id)) != null) {
                    tokentableMapper.updateByPrimaryKey(tokentable);
                } else {
                    tokentableMapper.insert(tokentable);
                }
//                tokentable类ru了个url
                tokentable.setUrl(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort());
                rep.setStatus(200);
                return tokentable;
            } else {
                rep.setStatus(201);
                return null;
            }
        }
    }

    @PostMapping(value = "/user/getUser")
    public @ResponseBody
    Object GetUser(HttpServletRequest req, HttpServletResponse rep) {
        String id = req.getParameter("id");
        user = userMapper.selectByPrimaryKey(Integer.valueOf(id));
        return user;
    }
}
