package com.mydata.crm.workbench.service.impl;

import com.mydata.crm.workbench.dao.ActivityRemarkDao;
import com.mydata.crm.workbench.domain.ActivityRemark;
import com.mydata.crm.workbench.service.ActivityRemarkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Resource
    private ActivityRemarkDao activityRemarkDao;
    @Override
    public List<ActivityRemark> getRemarkListByAid(String id) {
        List<ActivityRemark> activityRemarkList=activityRemarkDao.getRemarkListByAid(id);
        return activityRemarkList;
    }

    @Override
    public boolean removeRemark(String id) {
        boolean flag=true;
        int count=activityRemarkDao.removeRemark(id);
        System.out.println(count+"------");
        if (count!=1){
            flag=false;
        }
        return flag;
    }
}
