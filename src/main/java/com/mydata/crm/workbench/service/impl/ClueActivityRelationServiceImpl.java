package com.mydata.crm.workbench.service.impl;

import com.mydata.crm.workbench.dao.ClueActivityRelationDao;
import com.mydata.crm.workbench.service.ClueActivityRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {
    @Resource
    private ClueActivityRelationDao clueActivityRelationDao;
    @Override
    public boolean unbund(String id) {
        int count=clueActivityRelationDao.unbund(id);
        boolean flag=true;
        if (count!=1){
            flag=false;
        }
        return flag;
    }
}
