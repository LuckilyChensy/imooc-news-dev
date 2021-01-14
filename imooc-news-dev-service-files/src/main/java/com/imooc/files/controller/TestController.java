package com.imooc.files.controller;

import com.imooc.api.controller.user.TestControllerApi;
import com.imooc.grace.result.GraceJSONResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements TestControllerApi {

    final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Override
    public Object test() {
        return GraceJSONResult.ok("<h4>Test Spring Boot</h4>!");
    }

}
