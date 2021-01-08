package com.imooc.api.controller.user;

import org.springframework.web.bind.annotation.GetMapping;

public interface TestControllerApi {

    @GetMapping("/test")
    public Object test();

}
