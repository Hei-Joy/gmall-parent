package com.atguigu.gmall.portal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class GmallShopPortalApplicationTests {

    @Test
    public void contextLoads() {


        // 0
        // 10.0010932
        // 10.00434042

        //0.0001
        int i = new BigDecimal("10.00").compareTo(new BigDecimal("11.00"));
        System.out.println(i);
    }

}
