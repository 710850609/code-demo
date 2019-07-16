package org.linbo.demo.aop;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linbo.demo.aop.service.AccountService;
import org.linbo.demo.aop.vo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    private Account bob;

    @Before
    public void buildAccount() {
        bob = Account.builder().nickName("bob").build();
    }

    @Test
    public void testAdd() {
        accountService.add(bob);
    }

    @Test
    public void testAddWithError() {
        accountService.addWithError(bob);
    }
}
