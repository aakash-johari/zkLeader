package aj.zookeeper.controllers;

import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeaderController {

    private LeaderLatch leaderLatch;

    @Autowired
    public LeaderController(LeaderLatch leaderLatch) {
        this.leaderLatch = leaderLatch;
    }

    @GetMapping("/leader")
    public String isLeader() {
        return leaderLatch.hasLeadership() ? "Yes" : "No";
    }
}
