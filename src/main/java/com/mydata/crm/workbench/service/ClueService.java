package com.mydata.crm.workbench.service;

import com.mydata.crm.vo.PaginationVO;
import com.mydata.crm.workbench.domain.Clue;
import com.mydata.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface ClueService {
    boolean save(Clue clue);

    PaginationVO<Clue> pageList(Clue clue);

    Clue getClueById(String id);


    boolean convert(String clueId, Tran tran, String createBy);
}
