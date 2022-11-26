package jp.mirageworld.spring.common.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jp.mirageworld.spring.common.config.properties.MailProperties;
import jp.mirageworld.spring.common.model.Users;
import lombok.SneakyThrows;

@SpringBootTest
public class MailSenderServiceTest {
    
    @Autowired
    MailProperties mailProperties;

    @Autowired
    MailSenderService mailSenderService;

    @Autowired
    UsersService usersService;

    static final String SUBJECT  = "TEST";
    static final String BODY     = "送信テスト";

    @Test
    @SneakyThrows
    public void testSendText() {
        mailSenderService.sendText(mailProperties.getFrom(), mailProperties.getFrom(), SUBJECT, BODY);
    }

    @Test
    @SneakyThrows
    public void testSendTemplateSignup() {
        Optional<Users> users = usersService.findById(1);
        assertTrue(users.isPresent(), "システムアカウント未登録");
        mailSenderService.sendTtemplate(mailProperties.getFrom(), mailProperties.getFrom(), SUBJECT, "signup",users.get());
    }
}
