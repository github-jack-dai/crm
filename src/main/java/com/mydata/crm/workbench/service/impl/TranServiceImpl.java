package com.mydata.crm.workbench.service.impl;

import com.mydata.crm.utils.DateTimeUtil;
import com.mydata.crm.utils.UUIDUtil;
import com.mydata.crm.workbench.dao.CustomerDao;
import com.mydata.crm.workbench.dao.TranDao;
import com.mydata.crm.workbench.dao.TranHistoryDao;
import com.mydata.crm.workbench.domain.Customer;
import com.mydata.crm.workbench.domain.Tran;
import com.mydata.crm.workbench.domain.TranHistory;
import com.mydata.crm.workbench.service.TranService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TranServiceImpl implements TranService {
    @Resource
    private TranDao tranDao;
    @Resource
    private CustomerDao customerDao;
    @Resource
    private TranHistoryDao tranHistoryDao;
    @Override
    public boolean save(Tran tran, String customerName) {
        boolean flag=true;
        //判断客户是否已经存在
        Customer customer=customerDao.getCustomerByName(customerName);
        if (customer==null){
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            customer.setCreateBy(tran.getCreateBy());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setContactSummary(tran.getContactSummary());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setOwner(tran.getOwner());
            //添加客户
            int count1 = customerDao.save(customer);
            if(count1!=1){
                flag = false;
            }
        }
        //保存交易到数据库
        tran.setCustomerId(customer.getId());
        int count2=tranDao.save(tran);
        if(count2!=1){
            flag = false;
        }
        //添加交易历史
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setCreateBy(tran.getCreateBy());
        int count3 = tranHistoryDao.save(tranHistory);
        if(count3!=1){
            flag = false;
        }
        return flag;
    }
}
