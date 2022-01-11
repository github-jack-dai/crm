package com.mydata.crm.workbench.service.impl;

import com.mydata.crm.settings.dao.UserDao;
import com.mydata.crm.settings.domain.User;
import com.mydata.crm.vo.PaginationVO;
import com.mydata.crm.workbench.dao.ActivityDao;
import com.mydata.crm.workbench.dao.ActivityRemarkDao;
import com.mydata.crm.workbench.domain.Activity;
import com.mydata.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private UserDao userDao;

    @Resource
    private ActivityDao activityDao;

    @Resource
    private ActivityRemarkDao activityRemarkDao;

    @Override
    public boolean save(Activity activity) {
        int count=activityDao.save(activity);
        boolean flag=true;
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public PaginationVO<Activity> pageList(Activity activity) {
        List<Activity> list=activityDao.getActivityListByCondition(activity);
        int total=activityDao.getTotalByCondition(activity);
        PaginationVO<Activity> vo=new PaginationVO<Activity>();
        vo.setTotal(total);
        vo.setDataList(list);

        return vo;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag=true;
        //查询出需要删除的备注的数量
        int count1=activityRemarkDao.getCountByAids(ids);
        //删除备注，返回受到影响的条数
        int count2=activityRemarkDao.deleteByAids(ids);
        if (count1!=count2){
            flag=false;
        }
        //删除市场活动
        int count3=activityDao.delete(ids);
        if (count3!=ids.length){
            flag=false;
        }
        return true;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        List<User> userList = userDao.getUserList();
        Activity activity=activityDao.getById(id);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("uList",userList );
        map.put("a",activity);
        return map;
    }

    @Override
    public boolean update(Activity activity) {
        int count=activityDao.update(activity);
        boolean flag=true;
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        Activity activity=activityDao.datail(id);
        return activity;
    }

    @Override
    public List<Activity> getActivityListByClueId(String cid) {
        List<Activity> activityList=activityDao.getActivityListByClueId(cid);
        return activityList;

    }

    @Override
    public PaginationVO<Activity> getActivityListByName(String name,String cid) {
        PaginationVO<Activity> vo=new PaginationVO<Activity>();
        List<Activity> activityList=activityDao.getActivityListByName(name,cid);
        vo.setDataList(activityList);
        int count=activityDao.getTotalByName(name,cid);
        vo.setTotal(count);
        return vo;
    }
}
