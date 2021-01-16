package com.imooc.article.controller;

import com.imooc.api.controller.user.TestControllerApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements TestControllerApi {

    @Override
    @GetMapping("/test")
    public Object test() {
        return "<h3> Hello Spring Boot </h3>";
    }

}
