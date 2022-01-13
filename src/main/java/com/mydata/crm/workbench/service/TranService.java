package com.mydata.crm.workbench.service;

import com.mydata.crm.vo.PaginationVO;
import com.mydata.crm.workbench.domain.Tran;

public interface TranService {
    boolean save(Tran tran, String customerName);

    PaginationVO<Tran> pageList(Tran tran);
}
