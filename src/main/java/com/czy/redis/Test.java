package com.czy.redis;

import com.alibaba.fastjson.JSON;
import com.czy.htmlunit.Test1;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/4/21 0021.
 */
public class Test {
    @org.junit.Test
    public void test(){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        jedis.connect();

//        jedis.select(1);

//        String a = jedis.get("a");
//        System.out.print(a);
//        jedis.set("a", "b");

//        List list = new ArrayList();
//        list.add(1);
//        list.add(2);
//        Test1 tt = new Test1();
//        jedis.rpush("key", JSON.toJSONString(tt));
//        jedis.lpush("a", new String[]{"3qqqqqq", "4qqqqqq"});
        jedis.del("a");
        jedis.del("key");
        jedis.del("key".getBytes());
        System.out.println(jedis.get("key"));
        jedis.close();
    }
}
