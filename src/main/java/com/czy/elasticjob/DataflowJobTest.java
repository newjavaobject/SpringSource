package com.czy.elasticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/1 0001.
 */
public class DataflowJobTest implements DataflowJob<String> {
    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    static List<String> list_nodata = new ArrayList<String>();
    int i = 0;
//    static {
//        list_nodata.add("没有数据了！");
//    }
    public List<String> fetchData(ShardingContext shardingContext) {
        i++;
        String date = sdf.format(new Date());
        if (i % 10 == 0) {
            List<String> list = new ArrayList<String>();
            System.out.println("第 " + i + "个数据 -- " + date);
            list.add("第 " + i + "个数据!");
            return list;
        }else {
            System.out.println("第 " + i + "个数据 -- " + date);
            return null;
        }
    }

    public void processData(ShardingContext shardingContext, List<String> list) {
        if (list != null && list.size() > 0) {
            System.out.println("获取到数据：" + list.get(0) +" -- " + sdf.format(new Date()));
        }else System.out.println("没有获取到数据! --" + sdf.format(new Date()));
    }



    public static void main(String[] args) {
        new JobScheduler(createRegistryCenter(), createJobConfiguration()).init();
    }

    private static CoordinatorRegistryCenter createRegistryCenter() {
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration("localhost:2181", "elastic-job_dataflow");
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        regCenter.init();
        return regCenter;
    }

    private static LiteJobConfiguration createJobConfiguration() {
        // 定义作业核心配置
        JobCoreConfiguration dataflowCoreConfig = JobCoreConfiguration.newBuilder("demoSimpleJob", "0/5 * * * * ?", 1).build();
        // 定义DATAFLOW类型配置
        DataflowJobConfiguration dataflowJobConfig = new DataflowJobConfiguration(dataflowCoreConfig, DataflowJobTest.class.getCanonicalName(), true);
        // 定义Lite作业根配置
        LiteJobConfiguration dataflowJobRootConfig = LiteJobConfiguration.newBuilder(dataflowJobConfig).build();
        return dataflowJobRootConfig;
    }
}
