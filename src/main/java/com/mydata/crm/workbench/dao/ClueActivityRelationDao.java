package com.mydata.crm.workbench.dao;


import com.mydata.crm.workbench.domain.ClueActivityRelation;
import com.mydata.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueActivityRelationDao {

    int unbund(String id);

    int addRelationByActivityId(ClueActivityRelation clueActivityRelation);

    List<ClueActivityRelation> getListByClueId(String clueId);

    int delete(ClueActivityRelation clueActivityRelation);
}
