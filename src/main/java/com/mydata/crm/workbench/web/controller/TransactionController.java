package com.mydata.crm.workbench.web.controller;

import com.mydata.crm.settings.dao.UserDao;
import com.mydata.crm.settings.domain.User;
import com.mydata.crm.settings.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/workbench/transaction")
public class TransactionController {
    @Resource
    private UserService userService;
    @RequestMapping(value = "/save.do")
    public ModelAndView save(){
        ModelAndView mv=new ModelAndView();
        List<User> userList = userService.getUserList();
        mv.addObject("uList",userList);
        mv.setViewName("forward:/workbench/transaction/save.jsp");
        return mv;
    }
}
