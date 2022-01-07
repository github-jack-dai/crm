package com.mydata;

import com.mydata.crm.utils.DateTimeUtil;
import org.junit.Test;

public class Test01 {
    @Test
    public void test01(){
        //验证失效时间
        //失效时间
        String expiretime="2022-10-09 16:15:33";
        System.out.println(expiretime);
        //当前系统时间
        String currentTime = DateTimeUtil.getSysTime();
        System.out.println(currentTime);
        //compareTo验证时间大小，前面字符大返回正数，小则返回负数，相等返回0
        int i = expiretime.compareTo(currentTime);
        System.out.println(i);
    }
}
