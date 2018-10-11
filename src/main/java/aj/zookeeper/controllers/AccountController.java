package aj.zookeeper.controllers;

import aj.zookeeper.protobuf.account.model.AccountProto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @RequestMapping(value = "/accounts/{number}", produces = "application/x-protobuf")
    public AccountProto.Account findByNumber(@PathVariable("number") String number) {
        System.out.println(String.format("Account.findByNumber(%s)", number));
        return AccountProto.Account.newBuilder().setCustomerId((int)(Math.random() * 100.0))
                .setNumber(number)
                .build();
    }
}
