package com.mydata.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.mydata.crm.utils.DateTimeUtil;
import com.mydata.crm.utils.UUIDUtil;
import com.mydata.crm.vo.PaginationVO;
import com.mydata.crm.workbench.dao.*;
import com.mydata.crm.workbench.domain.*;
import com.mydata.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {
    //线索相关的表
    @Resource
    private ClueDao clueDao;
    @Resource
    private ClueActivityRelationDao clueActivityRelationDao;
    @Resource
    private ClueRemarkDao clueRemarkDao;
//    客户相关的表
    @Resource
    private CustomerDao customerDao;
    @Resource
    private CustomerRemarkDao customerRemarkDao;
//联系人相关的表
    @Resource
    private ContactsDao contactsDao;
    @Resource
    private ContactsRemarkDao contactsRemarkDao;
    @Resource
    private ContactsActivityRelationDao contactsActivityRelationDao;
    //交易相关的表
    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;


    @Override
    public boolean save(Clue clue) {
        boolean flag=true;
        int count=clueDao.save(clue);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public PaginationVO<Clue> pageList(Clue clue) {
        //获取查询的数据信息
//        PageHelper.startPage(1,1);
        List<Clue> clueList=clueDao.getClueListByCondition(clue);
        //获取查询的总记录条数
        int count=clueDao.getTotalByCondition(clue);
        PaginationVO<Clue> vo=new PaginationVO<Clue>();
        vo.setDataList(clueList);
        vo.setTotal(count);
        return vo;
    }

    @Override
    public Clue getClueById(String id) {
        Clue clue=clueDao.getClueById(id);
        return clue;
    }

    @Override
    public boolean convert(String clueId, Tran tran, String createBy) {
        String createTime=DateTimeUtil.getSysTime();
        boolean flag=true;
        //(1) 获取到线索id，通过线索id获取线索对象（线索对象当中封装了线索的信息）
        Clue c = clueDao.getClueById(clueId);
        System.out.println(1);
        //(2) 通过线索对象提取客户信息，当该客户不存在的时候，新建客户（根据公司的名称精确匹配，判断该客户是否存在！）
        String company = c.getCompany();
        Customer customer=customerDao.getCustomerByName(company);
        if (customer==null){
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(c.getAddress());
            customer.setName(company);
            customer.setCreateBy(createBy);
            customer.setCreateTime(createTime);
            customer.setWebsite(c.getWebsite());
            customer.setDescription(c.getDescription());
            customer.setContactSummary(c.getContactSummary());
            customer.setPhone(c.getPhone());
            customer.setNextContactTime(c.getNextContactTime());
            int count1=customerDao.save(customer);
            if (count1!=1){
                flag=false;
            }
        }/*else{
            customer.setAddress(c.getAddress());
            customer.setName(company);
            customer.setEditBy(createBy);
            customer.setEditTime(createTime);
            customer.setWebsite(c.getWebsite());
            customer.setDescription(c.getDescription());
            customer.setContactSummary(c.getContactSummary());
            customer.setPhone(c.getPhone());
            customer.setNextContactTime(c.getNextContactTime());
            int count1=customerDao.save(customer);
            if (count1!=1){
                flag=false;
            }
        }*/
        System.out.println(2);
        //(3) 通过线索对象提取联系人信息，保存联系人
        Contacts contacts=new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setAddress(c.getAddress());
        contacts.setAppellation(c.getAppellation());
        contacts.setContactSummary(c.getContactSummary());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setDescription(c.getDescription());
        contacts.setEmail(c.getEmail());
        contacts.setCustomerId(customer.getId());
        contacts.setJob(c.getJob());
        contacts.setFullname(c.getFullname());
        contacts.setMphone(c.getMphone());
        contacts.setSource(c.getSource());
        contacts.setNextContactTime(c.getNextContactTime());
        contacts.setOwner(c.getOwner());
        int count2=contactsDao.save(contacts);
        if (count2!=1){
            flag=false;
        }
        System.out.println(3);
        //(4) 线索备注转换到客户备注以及联系人备注
        List<ClueRemark> clueRemarkList=clueRemarkDao.getListByClueId(clueId);
        for (ClueRemark clueRemark:clueRemarkList){
            String noteContent=clueRemark.getNoteContent();
            CustomerRemark customerRemark=new CustomerRemark();
            customerRemark.setCreateBy(createBy);
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateTime(createTime);
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setEditFlag("0");
            customerRemark.setNoteContent(noteContent);
            int count3=customerRemarkDao.save(customerRemark);
            if (count3!=1){
                flag=false;
            }
            ContactsRemark contactsRemark=new ContactsRemark();
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setEditFlag("0");
            contactsRemark.setNoteContent(noteContent);
            int count4=contactsRemarkDao.save(contactsRemark);
            if (count4!=1){
                flag=false;
            }
        }
        System.out.println(4);
        //(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系
        List<ClueActivityRelation> clueActivityRelationList=clueActivityRelationDao.getListByClueId(clueId);
        for (ClueActivityRelation clueActivityRelation:clueActivityRelationList){
            String activityId=clueActivityRelation.getActivityId();
            ContactsActivityRelation contactsActivityRelation=new ContactsActivityRelation();
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            int count5=contactsActivityRelationDao.save(contactsActivityRelation);
            if (count5!=1){
                flag=false;
            }
        }
        System.out.println(5);
        //(6) 如果有创建交易需求，创建一条交易
        if (tran!=null){
            tran.setSource(c.getSource());
            tran.setOwner(c.getOwner());
            tran.setNextContactTime(c.getNextContactTime());
            tran.setDescription(c.getDescription());
            tran.setCustomerId(customer.getId());
            tran.setContactSummary(contacts.getContactSummary());
            tran.setContactsId(contacts.getId());
            int count6=tranDao.save(tran);
            if (count6!=1){
                flag=false;
            }
            System.out.println(6);
            //(7) 如果创建了交易，则创建一条该交易下的交易历史
            System.out.println(7);
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setStage(tran.getStage());
            tranHistory.setTranId(tran.getId());
            //添加交易历史
            int count7 = tranHistoryDao.save(tranHistory);
            if(count7!=1){
                flag = false;
            }
        }

        //(8) 删除线索备注
        for(ClueRemark clueRemark : clueRemarkList){

            int count8 = clueRemarkDao.delete(clueRemark);
            if(count8!=1){
                flag = false;
            }

        }
        System.out.println(8);
        //(9) 删除线索和市场活动的关系
        for(ClueActivityRelation clueActivityRelation : clueActivityRelationList){

            int count9 = clueActivityRelationDao.delete(clueActivityRelation);
            if(count9!=1){

                flag = false;

            }

        }
        System.out.println(9);
        //(10) 删除线索
        int count10 = clueDao.delete(clueId);
        if(count10!=1){
            flag = false;
        }
        System.out.println(10);
        return flag;
    }
}
