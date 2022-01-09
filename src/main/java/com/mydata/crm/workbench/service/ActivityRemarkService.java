package com.mydata.crm.workbench.service;

import com.mydata.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityRemarkService {
    List<ActivityRemark> getRemarkListByAid(String id);

    boolean removeRemark(String id);

    Map<String,Object> saveRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);
}
