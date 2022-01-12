package com.mydata.crm.workbench.service;

import com.mydata.crm.vo.PaginationVO;
import com.mydata.crm.workbench.domain.Contacts;

public interface ContactsService {
    PaginationVO<Contacts> getContactsListByName(String name);
}
