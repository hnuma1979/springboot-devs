package jp.mirageworld.spring.common.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jp.mirageworld.spring.common.model.Users;
import jp.mirageworld.spring.common.security.UserDetailsImpl;
import jp.mirageworld.spring.common.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ReactiveUserDetailsServiceImpl
    implements ReactiveUserDetailsService {

        final UsersService usersService;

        @Autowired
        public        ReactiveUserDetailsServiceImpl(UsersService usersService) {
            this.usersService=usersService;            
        }

        @Override
        public Mono<UserDetails> findByUsername(String username) {
            log.debug("findByUsername : START");
            try {
                Optional<Users> optional = usersService.findByUsername(username)
                    .or(()-> usersService.findByEmail(username));
                if (optional.isEmpty()) {
                    return Mono.empty();
                }
                return Mono.justOrEmpty( optional.map(UserDetailsImpl::new).get());
            } finally {
                log.debug("findByUsername : END");
            }
        }
    
}
