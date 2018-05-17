package com.e3mall.sso.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.e3mall.common.untils.CookieUtils;
import com.e3mall.common.untils.E3Result;
import com.e3mall.sso.service.LoginService;
import com.e3mall.sso.service.TokenService;
import com.e3mall.sso.service.UserService;
/**
 * @author zjt
 * @Description: 用户登陆
 */
@Controller
public class UserLoginController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;
	@Value("${TT_TOKEN}")
	private String TT_TOKEN;

	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	@ResponseBody
	public E3Result login(String username, String password,
			HttpServletRequest request, HttpServletResponse response){
		E3Result result = loginService.login(username, password);
		// 从返回结果中取token，写入cookie。Cookie要跨域。
		String token = result.getData().toString();
		//设置cookie cookie.js  $.cookie("TT_TOKEN") 中获取token 展示是否登录;
		CookieUtils.setCookie(request, response, TT_TOKEN, token);

		return result;
	}
	
	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback) {
		E3Result result = tokenService.getUserByToken(token);
		if (StringUtils.isNotBlank(callback)) {
			// 把结果封装成一个js语句响应
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return result;
	}

}
