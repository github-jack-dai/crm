package com.mydata.crm.settings.web.controller;

import com.mydata.crm.settings.domain.User;
import com.mydata.crm.exception.LoginException;
import com.mydata.crm.settings.service.UserService;
import com.mydata.crm.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/settings/user")
public class UserController{
    @Resource
    private UserService userService;
    //登录验证
    @ResponseBody
    @RequestMapping(value = "/login.do")
    public Map<String,Object> login(String loginAct,String loginPwd,HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        //接收浏览器端的ip地址
        String ip=request.getRemoteAddr();
        //接收异常消息的
        String msg="";
        //需要把页面传进来的密码转换为MD5加密形式的，不然数据库查不到
        loginPwd = MD5Util.getMD5(loginPwd);
        try {
            User user=userService.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user",user);
            map.put("success",true);
        } catch (LoginException e) {
            e.printStackTrace();
            //用户错误，返回的信息
            msg=e.getMessage();
            map.put("success",false);
            map.put("msg",msg);
        }
        return map;
    }
    /*private void login(HttpServletRequest request, HttpServletResponse response){
        System.out.println("进入验证登录的操作");
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        //将密码的明文形式转换成MD5的密文形式
        loginAct = MD5Util.getMD5(loginAct);
        loginPwd = MD5Util.getMD5(loginPwd);
        //接收浏览器端的ip地址
        String ip=request.getRemoteAddr();
        System.out.println("ip======"+ip);
        //未来业务层开发，统一使用代理类形态的接口对象
        UserService us= (UserService) ServiceFactory.getService(new UserServiceImpl());
        System.out.println(11);
        try{
            System.out.println("111");
            User user=us.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user",user);
            //如果程序执行到此处，说明业务层没有为controller抛出任何的异常
            // 表示登录成功
            PrintJson.printJsonFlag(response,true);
        }catch (Exception e){
            e.printStackTrace();
            //一旦程序执行了catch块的信息，说明业务层为我们验证登录失败，为controller抛出异常
            //表示登录失败
            String msg=e.getMessage();
            Map<String, Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }
    }*/
}
