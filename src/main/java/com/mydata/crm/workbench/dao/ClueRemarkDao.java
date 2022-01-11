package com.mydata.crm.workbench.dao;


import com.mydata.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {


    List<ClueRemark> getListByClueId(String clueId);

    int delete(ClueRemark clueRemark);
}
