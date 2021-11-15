package com.andon.springbootkafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Andon
 * 2021/11/11
 */
@Slf4j
@RestController
public class TestController {

    @GetMapping(value = "/test")
    public String test() {
        log.info("test!!");
        return "test!!";
    }
}
