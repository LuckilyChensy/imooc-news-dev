package com.imooc.user.controller;

import com.imooc.api.controller.user.TestControllerApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements TestControllerApi {

    final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Override
    public Object test(){

        logger.debug("debug: test spring framework ");
        logger.info("info: test spring framework ");
        logger.warn("warn: test spring framework ");
        logger.error("error: test spring framework ");

        return "<h3> test spring framework </h3>";

    }

}
