package jp.mirageworld.spring.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "app.mail")
@Validated
public class MailProperties {
    private String from = "example@example.com";
    private boolean send = false;
}
