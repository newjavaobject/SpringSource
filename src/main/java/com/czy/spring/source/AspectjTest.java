package com.czy.spring.source;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by Administrator on 2018/3/5 0005.
 */
@Aspect
public class AspectjTest {
    @Before(value = "execution(* com.czy.spring.source.SpringTest.*(**)")
    public void tttt(){
        System.out.println("前置通知!");
    }
}
