package com.mydata.crm.workbench.web.controller;

import com.github.pagehelper.PageHelper;
import com.mydata.crm.settings.dao.UserDao;
import com.mydata.crm.settings.domain.User;
import com.mydata.crm.settings.service.UserService;
import com.mydata.crm.utils.DateTimeUtil;
import com.mydata.crm.utils.UUIDUtil;
import com.mydata.crm.vo.PaginationVO;
import com.mydata.crm.workbench.domain.Tran;
import com.mydata.crm.workbench.service.CustomerService;
import com.mydata.crm.workbench.service.TranService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/workbench/transaction")
public class TransactionController {
    @Resource
    private UserService userService;
    @Resource
    private CustomerService customerService;
    @Resource
    private TranService tranService;
    @RequestMapping(value = "/getCharts.do")
    @ResponseBody
    public Map<String,Object> getCharts(){
        System.out.println("进来制作图表了");
        Map<String,Object> map=tranService.getCharts();
        return map;
    }
    @RequestMapping(value ="/changeStage.do")
    @ResponseBody
    public Map<String,Object> changeStage(Tran tran,HttpServletRequest request){
        tran.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        tran.setEditTime(DateTimeUtil.getSysTime());
        boolean flag=tranService.changeStage(tran);
        Map<String,String> pMap = (Map<String,String>)request.getServletContext().getAttribute("pMap");
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("success",flag);
        map.put("t",tran);
        map.put("possibility",pMap.get(tran.getStage()));
        return map;
    }
    @RequestMapping(value = "/detail.do")
    public ModelAndView detail(String id,HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
        Tran tran=tranService.detail(id);
        System.out.println(tran);
        Map<String,String> pMap = (Map<String,String>)request.getServletContext().getAttribute("pMap");
        String possibility= pMap.get(tran.getStage());
        //System.out.println(possibility);
        mv.addObject("t",tran);
        mv.addObject("possibility",possibility);
        mv.setViewName("workbench/transaction/detail");
        return  mv;
    }
    @RequestMapping(value = "/pageList.do")
    @ResponseBody
    public PaginationVO<Tran> pageList(Tran tran,String pageNo,String pageSize){
        System.out.println("进入到查询交易信息列表的操作（结合条件查询+分页查询）");
        PageHelper.startPage(Integer.valueOf(pageNo),Integer.valueOf(pageSize));
        PaginationVO<Tran> vo=tranService.pageList(tran);
        System.out.println(vo);
        return vo;
    }
    @RequestMapping(value = "/save.do")
    public ModelAndView save(Tran tran, String customerName, HttpServletRequest request){
        tran.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
        tran.setId(UUIDUtil.getUUID());
        tran.setCreateTime(DateTimeUtil.getSysTime());
        ModelAndView mv=new ModelAndView();
        boolean flag=tranService.save(tran,customerName);
       /*
       如果没有执行下面这句话的话会刷新当前页面一次，这里有优化的地方，
       比如加入异常提示，提示客户哪儿有问题，不过这里从业务逻辑来看，
       没什么需要提醒客户的，要提醒的话，前端就可以做到了，如果有啥
       特殊情况还是需要我们来解决，就直接重定向到首页吧，完美的操作就是
       在输出一个添加成功，这个功能已经实现，测试了很多次，基本没问题了
       * */
        if (flag){
            mv.addObject("success",flag);
            mv.setViewName("redirect:/workbench/transaction/index.jsp");
        }
        return mv;
    }
    @RequestMapping(value = "/jumpSave.do")
    public ModelAndView jumpSave(){
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
