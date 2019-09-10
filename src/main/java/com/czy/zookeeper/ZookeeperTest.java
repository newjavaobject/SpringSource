package com.czy.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2019/7/13 0013.
 * https://www.cnblogs.com/sunddenly/p/4064992.html
 */
public class ZookeeperTest {
    public static void main(String[] args) {
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper("localhost", 3000, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("触发了事件-->" + watchedEvent.getState());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (zooKeeper != null) {
            try {
                List<String> children = zooKeeper.getChildren("/watcher", false);
                System.out.println("/watcher节点数据-->" + new String(zooKeeper.getData("/watcher", false, new Stat())));
                for (String child : children) {
                    System.out.println("子节点ZNode-->" + child);
                }

                zooKeeper.close();
            } catch (KeeperException e) {
//                zooKeeper.create("/watcher", null, )
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void watcherTest(){
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper("localhost", 3000, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Watcher watcher = new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println("watchedEvent.getState()-->" + watchedEvent.getState());
            }
        };
        Stat stat = new Stat();

        if(zooKeeper != null) {
            try {
                while (true) {
                    byte[] data = zooKeeper.getData("/watcher", watcher, stat);
                    System.out.println("数据-->" + new String(data));
                }
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
