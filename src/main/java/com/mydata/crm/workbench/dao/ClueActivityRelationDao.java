package com.mydata.crm.workbench.dao;


import com.mydata.crm.workbench.domain.ClueActivityRelation;

public interface ClueActivityRelationDao {

    int unbund(String id);

    int addRelationByActivityId(ClueActivityRelation clueActivityRelation);
}
