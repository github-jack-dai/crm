package com.mydata.crm.workbench.web.controller;

import com.github.pagehelper.PageHelper;
import com.mydata.crm.settings.domain.User;
import com.mydata.crm.settings.service.UserService;
import com.mydata.crm.utils.DateTimeUtil;
import com.mydata.crm.utils.UUIDUtil;
import com.mydata.crm.vo.PaginationVO;
import com.mydata.crm.workbench.domain.Activity;
import com.mydata.crm.workbench.domain.Clue;
import com.mydata.crm.workbench.domain.ClueActivityRelation;
import com.mydata.crm.workbench.domain.Tran;
import com.mydata.crm.workbench.service.ActivityService;
import com.mydata.crm.workbench.service.ClueActivityRelationService;
import com.mydata.crm.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/workbench/clue")
public class ClueController {
    @Resource
    private UserService userService;
    @Resource
    private ClueService clueService;
    @Resource
    private ActivityService activityService;
    @Resource
    private ClueActivityRelationService clueActivityRelationService;
    @RequestMapping(value="/getUserList.do",method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUserList(){
        System.out.println("获取用户信息列表");
        List<User> userList=userService.getUserList();
        return userList;
    }
    @RequestMapping(value = "/save.do",method = RequestMethod.GET)
    @ResponseBody
    public boolean save(Clue clue, HttpServletRequest request){
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        System.out.println(clue);
        boolean flag=clueService.save(clue);
        return flag;
    }
    @RequestMapping(value = "/pageList.do",method = RequestMethod.GET)
    @ResponseBody
    public PaginationVO<Clue> pageList(Clue clue, String pageNo, String pageSize){
        /*
            分页查询的第一个参数表示略过的条数，第二个参数表示每页查询多少条数据
            细节注意，分页仅对第一个查询生效，后边的查询需要在添加才行
        * */
        PageHelper.startPage(Integer.valueOf(pageNo),Integer.valueOf(pageSize));
        PaginationVO<Clue> vo=clueService.pageList(clue);
        System.out.println(vo);
        return vo;
    }
    @RequestMapping(value = "/detail.do",method = RequestMethod.GET)
    public ModelAndView detail(String id){
        ModelAndView mv=new ModelAndView();
        Clue clue=clueService.getClueById(id);
        mv.addObject("c",clue);
        mv.setViewName("workbench/clue/detail");
        return mv;
    }
    @RequestMapping(value = "/getActivityListByClueId.do")
    @ResponseBody
    public List<Activity> getActivityListByClueId(String cid){
        List<Activity> activityList=activityService.getActivityListByClueId(cid);
        return activityList;
    }
    @RequestMapping("/unbund.do")
    @ResponseBody
    public boolean unbund(String id){
        boolean flag=clueActivityRelationService.unbund(id);
        return flag;
    }
    @RequestMapping("/getActivityListByName.do")
    @ResponseBody
    public PaginationVO<Activity> getActivityListByName(String name,String cid){
        System.out.println("name------"+name);
        System.out.println("cid------"+cid);
        PaginationVO<Activity> vo=activityService.getActivityListByName(name,cid);
        return vo;
    }
    @RequestMapping(value= "/addRelationByActivityId.do")
    @ResponseBody//又忘记添加注解了，裂开
    public boolean addRelationByActivityId(String id[],String cid){
        //字符串传值一定要用字符串不要用json串
        boolean flag=clueActivityRelationService.addRelationByActivityId(id,cid);
        return flag;
    }
    @RequestMapping(value = "/convert.do",method = RequestMethod.GET)
    public ModelAndView convert(String cid){
        ModelAndView mv=new ModelAndView();
        Clue clue=clueService.getClueById(cid);
        mv.addObject("c",clue);
        mv.setViewName("workbench/clue/convert");
        return mv;
    }
    @RequestMapping(value = "/convertDel.do")
    public ModelAndView convertDel(Tran tran,String clueId, String flag,HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        //是否需要创建交易
        if ("a".equals(flag)){
            tran.setId(UUIDUtil.getUUID());
            tran.setCreateBy(createBy);
            tran.setCreateTime(DateTimeUtil.getSysTime());
        }else {
            tran=null;
        }
        System.out.println("clueId======"+clueId);
        boolean flag1=clueService.convert(clueId,tran,createBy);
        if (flag1){
            mv.setViewName("workbench/clue/index");
        }
        return mv;
    }
}
