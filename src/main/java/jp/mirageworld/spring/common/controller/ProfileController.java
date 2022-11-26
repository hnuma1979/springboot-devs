package jp.mirageworld.spring.common.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.mirageworld.spring.common.form.ProfileForm;
import jp.mirageworld.spring.common.model.Users;
import jp.mirageworld.spring.common.security.UserDetailsImpl;
import jp.mirageworld.spring.common.service.UsersService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/profile")
public class ProfileController {

    final UsersService usersService;

    public ProfileController(@Autowired UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public String index(
        Model model, 
        @AuthenticationPrincipal UserDetailsImpl userDetail
    ) {
        ProfileForm form = new ProfileForm();
        BeanUtils.copyProperties(userDetail.getUsers(), form);

        model.addAttribute("form", form);
        
        return "profile/index";
    }

    @PostMapping
    @ResponseBody
    public List<String> post(
        @Validated @RequestBody  ProfileForm form,
        @AuthenticationPrincipal UserDetailsImpl userDetail
    ) {
        log.debug("START（post）");
        try {
            
            Users users = userDetail.getUsers();
            BeanUtils.copyProperties(form, users);
    
            usersService.update(users);
            
            return List.of("OK");
        } finally {
            log.debug("END（post）");
        }
    }

}
