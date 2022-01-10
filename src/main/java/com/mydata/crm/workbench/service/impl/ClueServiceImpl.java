package com.mydata.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.mydata.crm.vo.PaginationVO;
import com.mydata.crm.workbench.dao.ClueDao;
import com.mydata.crm.workbench.domain.Clue;
import com.mydata.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {
    @Resource
    private ClueDao clueDao;
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
}
