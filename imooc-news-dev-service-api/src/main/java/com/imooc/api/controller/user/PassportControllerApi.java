package com.imooc.api.controller.user;

import com.imooc.grace.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Api(value = "用户注册登录", tags = {"用户注册登录的controller"})
public interface PassportControllerApi {
    /**
     * 获取短信验证码方法的接口
     * @return
     */
    @ApiOperation(value = "获取短信验证码方法的接口", notes = "获取短信验证码方法的接口", httpMethod = "GET")
    @GetMapping("/getSMSCode")
    public GraceJSONResult getSMSCode(String mobile, HttpServletRequest request);

}
