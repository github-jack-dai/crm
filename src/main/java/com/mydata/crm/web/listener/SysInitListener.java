package com.mydata.crm.web.listener;

import com.mydata.crm.settings.domain.DicType;
import com.mydata.crm.settings.domain.DicValue;
import com.mydata.crm.settings.service.DicService;
import com.mydata.crm.settings.service.impl.DicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysInitListener implements ServletContextListener {
    /*
    此处用@Resource和@Autowired都会出现异常，卡住不动，暂时不知道为什么，还好有弹幕提醒
    经过自行百度，和多次测试得出结论各不一样，因为我没错误信息里我没看到空指针异常，但new出来的对象也无法使用
    @Autowired
    private DicService dicService;*/
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("服务器缓存处理数据字典开始");
        ServletContext application = servletContextEvent.getServletContext();
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath:conf/applicationContext.xml");
        DicService dicService= (DicService)context.getBean("dicService");
        //System.out.println("上下文对象被创建"+application);
        //System.out.println(dicService);
        //自己new出来的DicService无法使用，会出现错误，服务器停止，目前还不知道啥错误
        /*DicService dicService1=new DicServiceImpl();*/
        //System.out.println("dicService1==="+dicService1);
        Map<String,List<DicValue>> map=dicService.getAll();
        Set<String> set = map.keySet();
        for (String key:set) {
            application.setAttribute(key,map.get(key));
        }
        System.out.println("服务器缓存处理数据字典结束");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
