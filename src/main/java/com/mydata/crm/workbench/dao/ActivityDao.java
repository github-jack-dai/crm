package com.mydata.crm.workbench.dao;

import com.mydata.crm.workbench.domain.Activity;

import java.util.List;

public interface ActivityDao {
    int save(Activity activity);

    List<Activity> pageList(Activity activity);
}
