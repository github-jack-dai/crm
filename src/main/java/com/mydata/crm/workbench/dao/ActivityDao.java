package com.mydata.crm.workbench.dao;

import com.mydata.crm.workbench.domain.Activity;

import java.util.List;

public interface ActivityDao {
    int save(Activity activity);

    List<Activity> getActivityListByCondition(Activity activity);

    int getTotalByCondition(Activity activity);

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity activity);

    Activity datail(String id);
}
