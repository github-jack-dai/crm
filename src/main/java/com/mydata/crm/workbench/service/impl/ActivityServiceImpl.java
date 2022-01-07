package com.mydata.crm.workbench.service.impl;

import com.mydata.crm.workbench.dao.ActivityDao;
import com.mydata.crm.workbench.domain.Activity;
import com.mydata.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityDao activityDao;

    @Override
    public boolean save(Activity activity) {
        int count=activityDao.save(activity);
        boolean flag=true;
        if (count!=1){
            flag=false;
        }
        return flag;
    }
}
