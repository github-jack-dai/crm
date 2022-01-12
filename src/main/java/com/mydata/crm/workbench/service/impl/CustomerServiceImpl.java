package com.mydata.crm.workbench.service.impl;

import com.mydata.crm.workbench.dao.CustomerDao;
import com.mydata.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CustomerDao customerDao;
    @Override
    public List<String> getCustomerName(String name) {
        List<String> list=customerDao.getCustomerName(name);
        return list;
    }
}
