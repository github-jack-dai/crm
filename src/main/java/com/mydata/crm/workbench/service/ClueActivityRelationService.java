package com.mydata.crm.workbench.service;

public interface ClueActivityRelationService {
    boolean unbund(String id);

    boolean addRelationByActivityId(String ids[],String cid);
}
