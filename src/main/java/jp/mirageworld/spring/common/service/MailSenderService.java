package jp.mirageworld.spring.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import jp.mirageworld.spring.common.config.properties.MailProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailSenderService {
    
    final MailSender  mailSender;
    final MailProperties mailProperties;
    
    @Autowired
    public MailSenderService(
        MailSender      mailSender,
        MailProperties  mailProperties
    ) {
        this.mailSender         = mailSender;
        this.mailProperties     = mailProperties;
    }

    /**
     * メール送信処理
     * 
     * @param to
     * @param subject
     * @param body
     * @return
     * @throws Exception
     */
    public boolean send(String to, String subject, String body) throws Exception {
        return send(to, null, subject, body);
    }

    /**
     * メール送信処理
     * 
     * @param to
     * @param cc
     * @param subject
     * @param body
     * @return
     * @throws Exception
     */
    public boolean send(String to,String cc, String subject, String body) throws Exception {
        log.debug("send(from,cc,sub,body) : START");
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(mailProperties.getFrom()); // 送信元メールアドレス
            mailMessage.setTo(to);
            if (cc != null) {
                mailMessage.setCc(cc);
            }
            mailMessage.setSubject(subject);
            mailMessage.setText(body);

            if (mailProperties.isSend()) {
                mailSender.send(mailMessage);
            } else {
                log.debug("sub => {}, body => {}", subject, body);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        } finally {
            log.debug("send(from,cc,sub,body) : END");
        }
    }
}
