package jp.mirageworld.spring.common.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jp.mirageworld.spring.common.config.properties.MailProperties;
import lombok.SneakyThrows;

@SpringBootTest
public class MailSenderServiceTest {
    
    @Autowired
    MailProperties mailProperties;

    @Autowired
    MailSenderService mailSenderService;


    @Test
    @SneakyThrows
    public void test() {
        mailSenderService.send(
            mailProperties.getFrom(), 
            "TEST", 
            "送信テスト"
        );
    }
}
