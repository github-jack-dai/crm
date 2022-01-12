package com.mydata.crm.workbench.dao;

import com.mydata.crm.workbench.domain.Contacts;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContactsDao {


    int save(Contacts contacts);

    int getTotalByName(@Param("name") String name);

    List<Contacts> getContactsListByName(@Param("name") String name);
}
