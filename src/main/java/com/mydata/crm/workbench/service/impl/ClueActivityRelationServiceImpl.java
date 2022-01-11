package com.mydata.crm.workbench.service.impl;

import com.mydata.crm.utils.UUIDUtil;
import com.mydata.crm.workbench.dao.ClueActivityRelationDao;
import com.mydata.crm.workbench.domain.ClueActivityRelation;
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

    @Override
    public boolean addRelationByActivityId(String ids[],String cid) {
        ClueActivityRelation clueActivityRelation=new ClueActivityRelation();
        clueActivityRelation.setClueId(cid);
        boolean flag=true;
        for (String id:ids ) {
            //差点把这个写外面，造成主键不唯一了，还好提早发现了，不然异常有的找
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setActivityId(id);
            int count=clueActivityRelationDao.addRelationByActivityId(clueActivityRelation);
            if (count!=1){
                flag=false;
            }
        }
        return flag;
    }
}
