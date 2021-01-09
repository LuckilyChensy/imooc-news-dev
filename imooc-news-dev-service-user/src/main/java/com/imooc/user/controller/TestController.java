package com.imooc.user.controller;

import com.imooc.api.controller.user.TestControllerApi;
import com.imooc.grace.result.GraceJSONResult;
import com.imooc.grace.result.IMOOCJSONResult;
import com.imooc.utils.RedisOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements TestControllerApi {

    final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RedisOperator redis;

    @Override
    public Object test(){

        logger.debug("debug: test spring framework ");
        logger.info("info: test spring framework ");
        logger.warn("warn: test spring framework ");
        logger.error("error: test spring framework ");

//        return "<h3> test spring framework </h3>";
//        return IMOOCJSONResult.ok();
          return IMOOCJSONResult.ok("hello");

    }

    @GetMapping("/redis")
    public Object redis() {
        redis.set("age", "18");
        return GraceJSONResult.ok(redis.get("age"));
    }

}
