package aj.zookeeper.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class ZookeeperConfiguration {

    private final String zkConnectionString;
    private static Executor executor = Executors.newCachedThreadPool();

    @Autowired
    public ZookeeperConfiguration(@Value("${zookeeper.connectionString}") String zkConnectionString) {
        this.zkConnectionString = zkConnectionString;
    }

    @Bean(destroyMethod = "close")
    public CuratorFramework zooKeeper() throws InterruptedException {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(2000, Integer.MAX_VALUE);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(zkConnectionString, retryPolicy);
        curatorFramework.start();
        curatorFramework.blockUntilConnected();
        return curatorFramework;
    }

    @Bean(destroyMethod = "close")
    public LeaderLatch leaderLatch(CuratorFramework curatorFramework) throws Exception {
        LeaderLatch leaderLatch = new LeaderLatch(curatorFramework, "/zkLeader/leader");
        leaderLatch.start();

        LeaderLatchListener latchListener = new LeaderLatchListener() {
            @Override
            public void isLeader() {
                try {
                    System.out.println("I am leader, my name is " + InetAddress.getLocalHost().getHostAddress());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notLeader() {
                try {
                    System.out.println("I release my leader ship, my name is " + InetAddress.getLocalHost().getHostAddress());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        };

        leaderLatch.addListener(latchListener, executor);

        return leaderLatch;
    }
}
