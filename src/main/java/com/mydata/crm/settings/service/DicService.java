package com.mydata.crm.settings.service;


import com.mydata.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {
    Map<String,List<DicValue>> getAll();
}
