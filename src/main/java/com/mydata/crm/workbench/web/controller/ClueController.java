package com.mydata.crm.workbench.web.controller;

import com.mydata.crm.settings.domain.User;
import com.mydata.crm.settings.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/workbench/clue")
public class ClueController {
    @Resource
    private UserService userService;
    @RequestMapping(value="/getUserList.do",method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUserList(){
        System.out.println("获取用户信息列表");
        List<User> userList=userService.getUserList();
        return userList;
    }
}
