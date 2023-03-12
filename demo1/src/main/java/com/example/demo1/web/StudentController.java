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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class StudentController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private User user;
    @Autowired
    private TokentableMapper tokentableMapper;
    @Autowired
    private Tokentable tokentable;
    @PostMapping(value = "/student/register")
    public @ResponseBody
    Object studentRegister(HttpServletRequest req, HttpServletResponse rep) {
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        String username = req.getParameter("username");
        String name = req.getParameter("name");
        user = userMapper.selectByPrimaryKey(Integer.valueOf(id));
        if (user != null) {
            rep.setStatus(201);
            return null;
        } else {
            user = new User();
            user.setId(Integer.valueOf(id));
            user.setPassword(password);
            user.setName(name);
            user.setUsername(username);
            user.setPermission(1);
            userMapper.insertSelective(user);
            return user;
        }
    }
    @PostMapping(value = "/student/getAll")
    public @ResponseBody
    ArrayList<User> getAllStudent(HttpServletRequest req, HttpServletResponse rep) {
        ArrayList<User> students = new ArrayList<>();
        students = userMapper.$selectStudentsOrOps(1);
        rep.setStatus(200);
        return students;
    }
    @PostMapping(value = "/student/getStudent")
    public @ResponseBody
    User getStudent(HttpServletRequest req, HttpServletResponse rep) {
        String id = req.getParameter("id");
        user = new User();
        if(userMapper.selectByPrimaryKey(Integer.valueOf(id)) == null)
        {
            rep.setStatus(201);
            return null;
        }
        user = userMapper.selectByPrimaryKey(Integer.valueOf(id));
        rep.setStatus(200);
        return user;
    }
    @PostMapping(value = "/student/edit")
    public @ResponseBody
    User editStudent(HttpServletRequest req, HttpServletResponse rep) throws ParseException {
        user = new User();
        user.setId(Integer.valueOf(req.getParameter("id")));
        user.setUsername(req.getParameter("username"));
        user.setSex(req.getParameter("sex"));
        user.setPermission(Integer.valueOf(req.getParameter("permission")));
        user.setName(req.getParameter("name"));
        user.setPassword(req.getParameter("password"));
        user.setAddress(req.getParameter("address"));
        user.setEmail(req.getParameter("email"));
        user.setPhonenum(req.getParameter("phonenum"));
        user.setProfile(req.getParameter("profile"));
        String birthday = req.getParameter("birthday");
        birthday = birthday.replace("Z"," UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        user.setBirthday(simpleDateFormat.parse(birthday));
        userMapper.updateByPrimaryKeySelective(user);
        return user;
    }
    @PostMapping(value = "/student/delete")
    public @ResponseBody
    List<User> deleteStudent(HttpServletRequest req, HttpServletResponse rep) {
        ArrayList<User> students = new ArrayList<>();
        String id = req.getParameter("deleteid");
        if(userMapper.deleteByPrimaryKey(Integer.valueOf(id)) != 0)
            rep.setStatus(200);
        else
            rep.setStatus(201);
        students = userMapper.$selectStudentsOrOps(1);
        return students;
    }
    @PostMapping(value = "/student/changePassword")
    public @ResponseBody
    boolean changePassword(HttpServletRequest req, HttpServletResponse rep) {
        String id = req.getParameter("id");
        String oldPassword = req.getParameter("old");
        String newPassword = req.getParameter("new");
        if(userMapper.selectByPrimaryKey(Integer.valueOf(id)).getPassword().equals(oldPassword))
        {
            user.setId(Integer.valueOf(id));
            user.setPassword(newPassword);
            userMapper.updateByPrimaryKeySelective(user);
            return true;
        }
        return false;
    }
}
