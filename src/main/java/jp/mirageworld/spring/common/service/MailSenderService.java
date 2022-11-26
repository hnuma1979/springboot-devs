package jp.mirageworld.spring.common.service;

import java.util.Locale;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jp.mirageworld.spring.common.config.properties.MailProperties;
import jp.mirageworld.spring.common.model.Users;
import lombok.extern.slf4j.Slf4j;

/**
 * メール送信サービス。
 */
@Slf4j
@Service
public class MailSenderService {
    
    final  JavaMailSender mailSender;
    final MailProperties mailProperties;
    final TemplateEngine templateEngine;

    final Locale locale = Locale.JAPAN;
    
    /**
     * 標準コンストラクタ。
     * 
     * 直接呼ぶ必要はない
     * 
     * @param mailSender
     * @param mailProperties
     * @param templateEngine
     */
    @Deprecated
    @Autowired
    public MailSenderService(
        JavaMailSender  mailSender,
        MailProperties  mailProperties,
        TemplateEngine  templateEngine
    ) {
        this.mailSender         = mailSender;
        this.mailProperties     = mailProperties;
        this.templateEngine     = templateEngine;
    }

    /**
     * テキスト形式のメールを送信.
     * 
     * @param to            送信先（TO）
     * @param subject       件名
     * @param body          本文（TEXT)
     * @return              正常／異常
     * @throws Exception    実行時例外
     * @see #send(String, String, String, String, String)
     */
    public boolean sendText(String to, String subject, String body) throws Exception {
        return send(to, null, subject, body, null);
    }

    /**
     * テキスト形式のメールを送信.
     * 
     * @param to            送信先（TO）
     * @param cc            送信先（CC）
     * @param subject       件名
     * @param body          本文（TEXT)
     * @return              正常／異常
     * @throws Exception    実行時例外
     * @see #send(String, String, String, String, String)
     */
    public boolean sendText(String to,String cc, String subject, String body) throws Exception {
        return send(to, cc, subject, body, null);
    }

    /**
     * テキスト形式のメールを送信.
     * 
     * @param to            送信先（TO）
     * @param subject       件名
     * @param body          本文（TEXT)
     * @param html          本文（HTML)
     * @return              正常／異常
     * @throws Exception    実行時例外
     * @see #send(String, String, String, String, String)
     */
    public boolean send(String to, String subject, String body, String html) throws Exception {
        return send(to, null, subject, body, html);
    }

    /**
     * テンプレート使用のメールを送信.
     * 
     * @param to            送信先（TO）
     * @param cc            送信先（CC）
     * @param subject       件名
     * @param templateKey   テンプレートキー
     * @param Users         {@link Users}
     * @return              正常／異常
     * @throws Exception    実行時例外
     * @see #send(String, String, String, String, String)
     */
    public boolean sendTtemplate(String to, String subject, String templateKey, Users users) throws Exception {
        return sendTtemplate(to,null,subject,templateKey, users);
    }   

    /**
     * テンプレート使用のメールを送信.
     * 
     * @param to            送信先（TO）
     * @param cc            送信先（CC）
     * @param subject       件名
     * @param templateKey   テンプレートキー
     * @param Users         {@link Users}
     * @return              正常／異常
     * @throws Exception    実行時例外
     * @see #send(String, String, String, String, String)
     */
    public boolean sendTtemplate(String to,String cc, String subject, String templateKey, Users users) throws Exception {
         Context ctx = new Context();
        ctx.setVariable("users"  , users);

         String text = "";
         String html = "";
        
        try {
            text = this.templateEngine.process("_mail/"+templateKey+".txt", ctx);;
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        
        try {
            html = this.templateEngine.process("_mail/"+templateKey+".html", ctx);;
        } catch (Exception e) {
            log.debug(e.getMessage());
        }

        return send(to, cc, subject, text, html);
    }

    /**
     * テキスト形式のメールを送信.
     * 
     * @param to            送信先（TO）
     * @param cc            送信先（CC）
     * @param subject       件名
     * @param body          本文（TEXT)
     * @param html          本文（HTML)
     * @return              正常／異常
     * @throws Exception    実行時例外
     */
    boolean send(String to,String cc, String subject, String body, String html) throws Exception {
        log.debug("send(from,cc,sub,body) : START");
        try {
            if (!mailProperties.isSend()) {
                log.debug("sub => {}, body => {}, html => {}", subject, body, html);
                return true;
            }
            MimeMessage message         = mailSender.createMimeMessage();
            MimeMessageHelper helper    = new MimeMessageHelper(message, true);

            helper.setFrom(mailProperties.getFrom()); // 送信元メールアドレス
            helper.setTo(to);
            if (StringUtils.hasText(cc))  helper.setCc(cc);
            helper.setSubject(subject);
            if ( html == null ) {
                helper.setText(body);
            } else {
                helper.setText(body, html);
            }

            mailSender.send(message);
            return true;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        } finally {
            log.debug("send(from,cc,sub,body) : END");
        }
    }
}
