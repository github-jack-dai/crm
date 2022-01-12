package com.mydata.crm.workbench.web.controller;

import com.mydata.crm.settings.dao.UserDao;
import com.mydata.crm.settings.domain.User;
import com.mydata.crm.settings.service.UserService;
import com.mydata.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/workbench/transaction")
public class TransactionController {
    @Resource
    private UserService userService;
    @Resource
    private CustomerService customerService;
    @RequestMapping(value = "/save.do")
    public ModelAndView save(){
        ModelAndView mv=new ModelAndView();
        List<User> userList = userService.getUserList();
        mv.addObject("uList",userList);
        mv.setViewName("forward:/workbench/transaction/save.jsp");
        return mv;
    }
    @RequestMapping(value = "/getCustomerName.do")
    @ResponseBody
    public List<String> getCustomerName(String name){
        if (name==null || "".equals(name)){
            //这个输出语句不会执行，但是加上这句话确实可以防止删除为空时查询所有
            System.out.println("跳过一次");
            return null;
        }
        List<String> list=customerService.getCustomerName(name);
        System.out.println(list);
        return list;
    }
}
