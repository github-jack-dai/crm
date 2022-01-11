package com.mydata.crm.workbench.dao;

import com.mydata.crm.workbench.domain.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityDao {
    int save(Activity activity);

    List<Activity> getActivityListByCondition(Activity activity);

    int getTotalByCondition(Activity activity);

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity activity);

    Activity datail(String id);

    List<Activity> getActivityListByClueId(String cid);
    /*
    * 查询如果不用对象的话，一定要取别名，其他方法测试没有用，一定要记得*/
    List<Activity> getActivityListByName(@Param("name") String name,@Param("cid")String cid);

    int getTotalByName(@Param("name") String name,@Param("cid")String cid);
}
