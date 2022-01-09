package com.mydata.crm.workbench.web.controller;

import com.github.pagehelper.PageHelper;
import com.mydata.crm.settings.domain.User;
import com.mydata.crm.settings.service.UserService;
import com.mydata.crm.utils.DateTimeUtil;
import com.mydata.crm.utils.UUIDUtil;
import com.mydata.crm.vo.PaginationVO;
import com.mydata.crm.workbench.domain.Activity;
import com.mydata.crm.workbench.domain.ActivityRemark;
import com.mydata.crm.workbench.service.ActivityRemarkService;
import com.mydata.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    @Resource
    private ActivityRemarkService activityRemarkService;

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
       //uuid作为主键，外加记录创建人和创建时间
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
    public PaginationVO<Activity> pageList(Activity activity, String pageNo, String pageSize){
        System.out.println("进入到查询市场活动信息列表的操作（结合条件查询+分页查询）");
        //分页查询的第一个参数表示略过的条数，第二个参数表示每页查询多少条数据
        PageHelper.startPage(Integer.valueOf(pageNo),Integer.valueOf(pageSize));
        PaginationVO<Activity> vo=activityService.pageList(activity);
        return vo;
    }

    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public boolean delete(HttpServletRequest request,String id[]){
        //传统servlet写法
        /*String ids[] = request.getParameterValues("id");
        System.out.println(ids.length);*/
        System.out.println("id==="+id.length);
        boolean flag=activityService.delete(id);
        System.out.println(flag);
        return flag;
    }
    @RequestMapping(value = "/getUserListAndActivity.do",method =RequestMethod.GET )
    @ResponseBody
    public Map<String,Object> getUserListAndActivity(String id){
        Map<String,Object> map=activityService.getUserListAndActivity(id);
        return map;
    }
    @RequestMapping(value = "/update.do")
    @ResponseBody//这个一定要开始的时候就加上，不然老忘记
    public boolean update(Activity activity,HttpServletRequest request){
        //修改人和修改时间
        activity.setEditTime(DateTimeUtil.getSysTime());
        //通过request对象获取session里的用户，在强转获取用户名字
        activity.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        boolean flag=activityService.update(activity);
        System.out.println("flag====="+flag);
        return flag;
    }
    @RequestMapping(value = "/detail.do",method =RequestMethod.GET)
    @ResponseBody//这个一定要开始的时候就加上，不然老忘记
    public ModelAndView detail(String id){
        Activity activity=activityService.detail(id);
        System.out.println(activity);
        ModelAndView mv=new ModelAndView();
        mv.addObject("a",activity);
        mv.setViewName("workbench/activity/detail");
        return mv;
    }
    @RequestMapping(value = "/getRemarkListByAid.do",method =RequestMethod.GET)
    @ResponseBody//这个一定要开始的时候就加上，不然老忘记
    public List<ActivityRemark> getRemarkListByAid(String activityId){
        List<ActivityRemark> activityRemarkList=activityRemarkService.getRemarkListByAid(activityId);
        System.out.println(activityRemarkList);
        return activityRemarkList;
    }
    @RequestMapping(value = "/removeRemark.do")
    @ResponseBody//这个一定要开始的时候就加上，不然老忘记
    public boolean removeRemark(String id){
        boolean flag=activityRemarkService.removeRemark(id);
        return flag;
    }
    @RequestMapping(value = "/saveRemark.do")
    @ResponseBody//这个一定要开始的时候就加上，不然老忘记
    public Map<String,Object> saveRemark(String noteContent,HttpServletRequest request,String activityId){
        System.out.println("开始执行描述的添加操作");
        ActivityRemark activityRemark=new ActivityRemark();
        activityRemark.setNoteContent(noteContent);
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
        activityRemark.setEditFlag("0");
        activityRemark.setActivityId(activityId);
        Map<String,Object> map=activityRemarkService.saveRemark(activityRemark);
        return map;
    }
    @RequestMapping(value = "/updateRemark.do")
    @ResponseBody//这个一定要开始的时候就加上，不然老忘记
    public Map<String,Object> updateRemark(String id,String noteContent,HttpServletRequest request){
        ActivityRemark activityRemark=new ActivityRemark();
        activityRemark.setId(id);
        activityRemark.setEditFlag("1");
        activityRemark.setNoteContent(noteContent);
        activityRemark.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        boolean flag=activityRemarkService.updateRemark(activityRemark);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("success",flag);
        map.put("ar",activityRemark);
        return map;
    }
}
