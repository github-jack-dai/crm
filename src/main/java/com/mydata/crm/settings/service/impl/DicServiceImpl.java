package com.mydata.crm.settings.service.impl;

import com.mydata.crm.settings.dao.DicTypeDao;
import com.mydata.crm.settings.dao.DicValueDao;
import com.mydata.crm.settings.domain.DicType;
import com.mydata.crm.settings.domain.DicValue;
import com.mydata.crm.settings.service.DicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {
    @Resource
    private DicTypeDao dicTypeDao;
    @Resource
    private DicValueDao dicValueDao;

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map=new HashMap<String, List<DicValue>>();
        //将字典类型列表取出
        List<DicType> dicTypeList=dicTypeDao.getTypeList();
        //将字典类型列表遍历
        for(DicType dicType:dicTypeList){
            String code=dicType.getCode();
            //根据每一个字典类型来取得字典值列表
            List<DicValue> dicValueList=dicValueDao.getListByCode(code);
            map.put(code+"List",dicValueList);
        }
        return map;
    }
}
