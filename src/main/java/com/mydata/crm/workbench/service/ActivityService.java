package com.mydata.crm.workbench.service;

import com.mydata.crm.vo.PaginationVO;
import com.mydata.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    boolean save(Activity activity);

    PaginationVO<Activity> pageList(Activity activity);

    boolean delete(String[] id);

    Map<String,Object> getUserListAndActivity(String id);

    boolean update(Activity activity);

    Activity detail(String id);

    List<Activity> getActivityListByClueId(String cid);

    PaginationVO<Activity> getActivityListByName(String name);
}
