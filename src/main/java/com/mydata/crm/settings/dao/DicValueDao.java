package com.mydata.crm.settings.dao;


import com.mydata.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {

    List<DicValue> getListByCode(String code);
}
