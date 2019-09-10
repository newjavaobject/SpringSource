package com.czy.quartz;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.utils.Key;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

import java.util.Collection;

import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.DateBuilder.*;

/**
 * Created by Administrator on 2019/6/19 0019.
 */
public class QuartzTest {
    public static void main(String[] args) throws SchedulerException, NoSuchMethodException, ClassNotFoundException, InterruptedException {
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        Scheduler sched = schedFact.getScheduler();
        sched.start();

        MethodInvokingJobDetailFactoryBean bean1= new MethodInvokingJobDetailFactoryBean();
        bean1.setName("task1");bean1.setGroup("taskGroup1");bean1.setTargetClass(Task1.class);
        bean1.setTargetObject(new Task1());bean1.setTargetMethod("run1");bean1.afterPropertiesSet();
        JobDetail job1 = bean1.getObject();
        Trigger trigger1 = newTrigger().withIdentity("myTrigger1", "triggerGroup1").startNow().withSchedule(cronSchedule("0/2 * * * * ?")).build();
        sched.scheduleJob(job1, trigger1);//执行任务1

        MethodInvokingJobDetailFactoryBean bean2= new MethodInvokingJobDetailFactoryBean();
        bean2.setName("task2");bean2.setGroup("taskGroup1");bean2.setTargetClass(Task2.class);
        bean2.setTargetObject(new Task2());bean2.setTargetMethod("run2");bean2.afterPropertiesSet();
        JobDetail job2 = bean2.getObject();
        Trigger trigger2 = newTrigger().withIdentity("myTrigger2", "triggerGroup1").startNow().withSchedule(cronSchedule("0/5 * * * * ?")).build();
        sched.scheduleJob(job2, trigger2);//执行任务2

        Thread.sleep(3000);
        for(Scheduler scheduler : schedFact.getAllSchedulers()) {
            for (String group : scheduler.getTriggerGroupNames()) {
                GroupMatcher<TriggerKey> matcher = GroupMatcher.groupEquals(group);
                for (TriggerKey triggerKey : scheduler.getTriggerKeys(matcher)) {
                    System.out.println("triggerKey ---------> "+triggerKey.getName());
                }
            }
        }
    }
}
class Task1{
    public void run1() {
        System.out.println("执行任务1.");
    }
}
class Task2{
    public void run2() {
        System.out.println("执行任务2.");
    }
}