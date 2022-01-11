package com.mydata.crm.workbench.dao;


import com.mydata.crm.workbench.domain.Clue;

import java.util.List;

public interface ClueDao {

    int save(Clue clue);

    List<Clue> getClueListByCondition(Clue clue);

    int getTotalByCondition(Clue clue);

    Clue getClueById(String id);

    int delete(String clueId);
}
