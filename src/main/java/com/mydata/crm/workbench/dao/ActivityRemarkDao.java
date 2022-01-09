package com.mydata.crm.workbench.dao;

import com.mydata.crm.workbench.domain.ActivityRemark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String id);

    int removeRemark(String id);

    int saveRemark(ActivityRemark activityRemark);

    /*
    为毛这个不能单独传参，一定要传个对象才行,
    经过测试如果传入的不是对象，一定要加别名@Param("别名")
     */
    int updateRemark(ActivityRemark activityRemark);
}
