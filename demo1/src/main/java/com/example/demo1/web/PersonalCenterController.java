package com.example.demo1.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PersonalCenterController {
    @RequestMapping(value = "/personalCenter")
    public String index(){
        return "personalCenter";
    }
}
