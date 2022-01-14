package com.mydata.crm.workbench.service;

import com.mydata.crm.vo.PaginationVO;
import com.mydata.crm.workbench.domain.Tran;

import java.util.Map;

public interface TranService {
    boolean save(Tran tran, String customerName);

    PaginationVO<Tran> pageList(Tran tran);

    Tran detail(String id);

    boolean changeStage(Tran tran);

    Map<String,Object> getCharts();
}
