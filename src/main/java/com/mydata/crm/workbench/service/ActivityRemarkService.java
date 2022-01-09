package com.mydata.crm.workbench.service;

import com.mydata.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {
    List<ActivityRemark> getRemarkListByAid(String id);

    boolean removeRemark(String id);

    boolean saveRemark(ActivityRemark activityRemark);
}
