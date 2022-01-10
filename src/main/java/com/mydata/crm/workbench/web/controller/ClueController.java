package com.mydata.crm.workbench.web.controller;

import com.github.pagehelper.PageHelper;
import com.mydata.crm.settings.domain.User;
import com.mydata.crm.settings.service.UserService;
import com.mydata.crm.utils.DateTimeUtil;
import com.mydata.crm.utils.UUIDUtil;
import com.mydata.crm.vo.PaginationVO;
import com.mydata.crm.workbench.domain.Clue;
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
        return  mv;
    }
}
