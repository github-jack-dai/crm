package com.mydata.crm.workbench.service;

import com.mydata.crm.workbench.domain.Activity;

import java.util.List;

public interface ActivityService {
    boolean save(Activity activity);

    List<Activity> pageList(Activity activity);
}
