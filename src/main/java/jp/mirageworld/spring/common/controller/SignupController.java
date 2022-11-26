package jp.mirageworld.spring.common.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.mirageworld.spring.common.form.SignupForm;
import jp.mirageworld.spring.common.model.Users;
import jp.mirageworld.spring.common.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequestMapping(path = "/signup")
public class SignupController {

    final UsersService usersService;

    public SignupController(UsersService usersService) {
        log.debug("INIT");
        this.usersService = usersService;
    }

    @GetMapping
    public String  index() {
        log.debug("START（index）");
        try {
            return "signup/index";
        } finally {
            log.debug("END（index）");
        }
    }
    
    @PostMapping
    @ResponseBody
    public Mono<Users> post(@Validated @RequestBody  SignupForm form) {
        log.debug("START（post）");
        try {
            Users users = new Users();
            BeanUtils.copyProperties(form, users);
    
            // アカウント初期設定
            users.setEnabled(true);
            users.setLocked(false);
    
            usersService.insert(users);
            return Mono.just(users);
        } finally {
            log.debug("END（post）");
        }
    }
}
