package aj.zookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ZookeeperLeaderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZookeeperLeaderApplication.class, args);
    }
}
