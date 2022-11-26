package jp.mirageworld.spring.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
         UserDetails user = User.withDefaultPasswordEncoder()
              .username("user")
              .password("password")
              .roles("USER")
              .build();
         return new MapReactiveUserDetailsService(user);
    }

    @Bean
    protected SecurityWebFilterChain  httpSecurity(ServerHttpSecurity http) throws Exception {
        log.debug("httpSecurity(http) : START");
        try {
            return http
                // 認可
                .authorizeExchange(
                    a->a.pathMatchers(
                        "/",
                        "/login**",
                        "/signup**",
                        "/css/**",
                        "/js/**"
                    )
                    .permitAll()
                    .anyExchange().authenticated()
                )

                // FORM 認証
                .formLogin(
                    f->f.loginPage("/login")
                )

                // BASIC 認証
                .httpBasic(b->b.disable())

                // ポスト制限（CSRF）
                .csrf(c->c.disable())

                // 匿名ログイン
                .anonymous(a->a.disable())
            .build();
        } finally {
            log.debug("httpSecurity(http) : END");
        }
    }

}