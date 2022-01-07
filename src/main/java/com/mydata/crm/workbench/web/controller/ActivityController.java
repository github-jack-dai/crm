package com.mydata.crm.workbench.web.controller;

import com.github.pagehelper.PageHelper;
import com.mydata.crm.settings.domain.User;
import com.mydata.crm.settings.service.UserService;
import com.mydata.crm.utils.DateTimeUtil;
import com.mydata.crm.utils.UUIDUtil;
import com.mydata.crm.workbench.domain.Activity;
import com.mydata.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/workbench/activity")
public class ActivityController {
    @Resource
    private UserService userService;
    @Resource
    private ActivityService activityService;

    @RequestMapping(value="/getUserList.do",method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUserList(){
        System.out.println("获取用户信息列表");
        List<User> userList=userService.getUserList();
        return userList;
    }
    @RequestMapping(value="/save.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Boolean> save(HttpServletRequest request, Activity activity){ ;
        Map<String,Boolean> map=new HashMap<String,Boolean>();
        //这传统servlet的方法，springmvc可以直接用对象接收
       /* String startDate = request.getParameter("startDate");
        System.out.println(startDate);*/
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        //通过request对象获取session里的用户，在强转获取用户名字
        activity.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
        System.out.println(activity);
        boolean flag=activityService.save(activity);
        map.put("success",flag);
        return map;
    }
    @RequestMapping(value = "/pageList.do",method = RequestMethod.GET)
    @ResponseBody
    public List<Activity> pageList(Activity activity,String pageNo,String pageSize){
        System.out.println("进入到查询市场活动信息列表的操作（结合条件查询+分页查询）");
        PageHelper.startPage(Integer.valueOf(pageNo),Integer.valueOf(pageSize));
        List<Activity> list=activityService.pageList(activity);
        for(Activity ac:list){
            System.out.println(ac);
        }
        return list;
    }
}
