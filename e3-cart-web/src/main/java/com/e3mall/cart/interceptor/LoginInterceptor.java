package com.e3mall.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.e3mall.common.jedis.JedisClient;
import com.e3mall.common.untils.CookieUtils;
import com.e3mall.common.untils.E3Result;
import com.e3mall.pojo.TbUser;
import com.e3mall.sso.service.TokenService;

public class LoginInterceptor implements HandlerInterceptor{

	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TokenService tokenService;
	@Value("${COOKIE_TOKEN_KEY=token}")
	private String COOKIE_TOKEN_KEY;

	@Override
	//先进行处理
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		//从cookie中取token
		String token = CookieUtils.getCookieValue(request, COOKIE_TOKEN_KEY);

		//判断是否登录
		if (StringUtils.isBlank(token)) {
			return true;
		}
		E3Result e3Result  = tokenService.getUserByToken(token);
		if (e3Result.getStatus() != 200) {
			return true;
		}
		//取到用户信息。登录状态。
		TbUser user = (TbUser) e3Result.getData();
		//6.把用户信息放到request中。只需要在Controller中判断request中是否包含user信息。放行
		request.setAttribute("user", user);
		return true;
	}

	@Override
	//handler执行之后，返回ModeAndView之前
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	//handler执行之后，返回ModeAndView之后
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
