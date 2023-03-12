package com.example.demo1.web;

import com.example.demo1.mapper.TokentableMapper;
import com.example.demo1.mapper.UserMapper;
import com.example.demo1.model.Tokentable;
import com.example.demo1.model.User;
import com.example.demo1.utils.TokenProccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ServerController {
    @PostMapping(value = "/server/url")
    public @ResponseBody
     String getUrl(HttpServletRequest req, HttpServletResponse rep) {
        String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort();
        return url;
    }
}
