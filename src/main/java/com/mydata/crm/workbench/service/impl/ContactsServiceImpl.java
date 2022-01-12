package com.mydata.crm.workbench.service.impl;

import com.mydata.crm.vo.PaginationVO;
import com.mydata.crm.workbench.dao.ContactsDao;
import com.mydata.crm.workbench.domain.Contacts;
import com.mydata.crm.workbench.service.ContactsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ContactsServiceImpl implements ContactsService {
    @Resource
    private ContactsDao contactsDao;
    @Override
    public PaginationVO<Contacts> getContactsListByName(String name) {
        PaginationVO<Contacts> vo=new PaginationVO<Contacts>();
        int count=contactsDao.getTotalByName(name);
        System.out.println(1111);
        List<Contacts> contactsList=contactsDao.getContactsListByName(name);
        System.out.println(2222);
        vo.setTotal(count);
        vo.setDataList(contactsList);
        return vo;
    }
}
