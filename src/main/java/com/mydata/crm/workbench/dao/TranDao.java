package com.mydata.crm.workbench.dao;

import com.mydata.crm.workbench.domain.Tran;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int save(Tran tran);

    List<Tran> getTranListByCondition(Tran tran);

    int getTotalByCondition(Tran tran);

    Tran detail(@Param("id") String id);

    int changeStage(Tran tran);
}
