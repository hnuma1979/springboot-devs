package jp.mirageworld.spring.common.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    

    @Autowired
    MessageSource messageSource;

    Locale locale = Locale.JAPAN;

    /**
     * システムで統一の言語でメッセージを出力する.
     * 
     * @see MessageSource#getMessage(String, Object[], Locale)
     * 
     * @param code 
     * @param args
     * @return
     */
    public String getMessage(String code, Object ... args) {
        return this.getMessage(code, locale, args);
    }

    /**
     * 言語でメッセージを出力する.
     * 
     * @see MessageSource#getMessage(String, Object[], Locale)
     * 
     * @param code
     * @param locale
     * @param args
     * @return
     */
    public String getMessage(String code, Locale locale,Object ... args) {
        return messageSource.getMessage(code, args, locale);
    }
    
}
