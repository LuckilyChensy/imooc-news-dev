package com.imooc.api.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

@Api(value = "Test controller 的标题", tags = {"swagger2 文档测试"})
public interface TestControllerApi {

    @ApiOperation(value = "Test controller 方法的接口", notes = "Test controller 方法的接口", httpMethod = "GET")
    @GetMapping("/test")
    public Object test();

}
