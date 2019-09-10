package com.czy.elasticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/3/1 0001.
 */
public class DataflowJobTest implements DataflowJob<String> {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int i = 0;

    public List<String> fetchData(ShardingContext shardingContext) {
        List<String> list = new ArrayList<String>();
        switch (shardingContext.getShardingItem()) {
            case 0:
                while (i++ <= 100001) {
                    list.add(i+"");
                    if (i % 10 == 0) {
                        System.out.println("list长度："+list.size());
                        return list;
                    }
                }
            case 2:
                list.add("2");break;
            case 1:
                list.add("1");break;
        }

        return null;
    }

    public void processData(ShardingContext shardingContext, List<String> list) {
//        System.out.println("睡眠之前。");
//        try {
//            TimeUnit.SECONDS.sleep(10);
        if (list == null) {
            return;
        }
            System.out.println(list);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("睡眠之后。");
    }



    public static void main(String[] args) {
        new JobScheduler(createRegistryCenter(), createJobConfiguration()).init();
    }

    private static CoordinatorRegistryCenter createRegistryCenter() {
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration("localhost:2181", "elastic-job2");
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        regCenter.init();
        return regCenter;
    }

    private static LiteJobConfiguration createJobConfiguration() {
        // 定义作业核心配置
        JobCoreConfiguration dataflowCoreConfig = JobCoreConfiguration.newBuilder("demoSimpleJob5", "30 35 23 12 04 ?", 1)
                .shardingItemParameters("0=0").build();
        // 定义DATAFLOW类型配置
        DataflowJobConfiguration dataflowJobConfig = new DataflowJobConfiguration(dataflowCoreConfig, DataflowJobTest.class.getCanonicalName(), true);
        // 定义Lite作业根配置
        LiteJobConfiguration dataflowJobRootConfig = LiteJobConfiguration.newBuilder(dataflowJobConfig).build();
        return dataflowJobRootConfig;
    }
}
