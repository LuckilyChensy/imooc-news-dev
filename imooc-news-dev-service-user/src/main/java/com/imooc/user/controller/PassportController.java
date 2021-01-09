package com.imooc.user.controller;

import com.imooc.api.controller.BaseController;
import com.imooc.api.controller.user.PassportControllerApi;
import com.imooc.api.controller.user.TestControllerApi;
import com.imooc.enums.UserStatus;
import com.imooc.grace.result.GraceJSONResult;
import com.imooc.grace.result.IMOOCJSONResult;
import com.imooc.grace.result.ResponseStatusEnum;
import com.imooc.pojo.AppUser;
import com.imooc.pojo.bo.RegistLoginBO;
import com.imooc.user.service.UserService;
import com.imooc.utils.IPUtil;
import com.imooc.utils.RedisOperator;
import com.imooc.utils.SMSUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
public class PassportController extends BaseController implements PassportControllerApi {

    final static Logger logger = LoggerFactory.getLogger(PassportController.class);

    @Autowired
    private SMSUtils smsUtils;

    @Autowired
    private UserService userService;

    @Override
    public GraceJSONResult getSMSCode(String mobile, HttpServletRequest request) {

        // 获得用户ip
        String userIp = IPUtil.getRequestIp(request);

        // 根据用户的ip进行限制，限制用户在60秒内只能获得一次验证码
        redis.setnx60s(MOBILE_SMSCODE + ":" + userIp, userIp);

        // 生成随机验证码并且发送短信
        String random = (int)((Math.random() * 9 + 1) * 100000) + "";
//        smsUtils.sendSMS(MyInfo.getMobile(), random);

        // 把验证码存入redis，用于后续进行验证
        redis.set(MOBILE_SMSCODE + ":" + mobile, random, 30 * 60);

        return GraceJSONResult.ok();

    }

    @Override
    public GraceJSONResult doLogin(@Valid RegistLoginBO registLoginBO, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        // 判断 BindingResult 中是否保存了错误的验证信息，如果有，则需要返回
        if(result.hasErrors()){
            Map<String,String> map = getErrors(result);
            return GraceJSONResult.errorMap(map);
        }

        String mobile = registLoginBO.getMobile();
        String smsCode = registLoginBO.getSmsCode();

        // 1. 校验验证码是否匹配
        String redisSMSCode = redis.get(MOBILE_SMSCODE + ":" + mobile);

        if (StringUtils.isBlank(redisSMSCode) || !redisSMSCode.equalsIgnoreCase(smsCode)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.SMS_CODE_ERROR);
        }

        // 2. 查询数据库，判断该用户注册
        AppUser user = userService.queryMobileIsExist(mobile);
        if (user != null && user.getActiveStatus() == UserStatus.FROZEN.type) {
            // 如果用户不为空，并且状态为冻结，则直接抛出异常，禁止登录
            return GraceJSONResult.errorCustom(ResponseStatusEnum.USER_FROZEN);
        } else if (user == null) {
            // 如果用户没有注册过，则为null，需要注册信息入库
            user = userService.createUser(mobile);
        }

        return GraceJSONResult.ok(user);
    }

}
