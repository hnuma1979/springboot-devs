package jp.mirageworld.spring.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class IndexController {

    
    @GetMapping
    public String index() {
        log.debug("index : START");
        try {
            return "index";
        } finally {
            log.debug("index : END");
        }
    }
}
