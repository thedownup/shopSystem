package com.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.common.untils.E3Result;
import com.e3mall.pojo.TbUser;
import com.e3mall.sso.service.UserService;

/**
 * 
 * @author zjt
 * @Description: 注册专用
 */
@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/register")
	public String showRegister(){
		return "register";
	}
	
	/**校验数据*/
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public E3Result checkData(@PathVariable String param, @PathVariable Integer type) {
		E3Result e3Result = userService.checkData(param, type);
		return e3Result;
	}
	
	
	/**注册*/
	@RequestMapping(value="/user/register", method=RequestMethod.POST)
	@ResponseBody
	public E3Result register(TbUser user) {
		E3Result e3Result = userService.register(user);
		return e3Result;
	}
	
	/**页面控制*/
	@RequestMapping("/page/login")
	public String showLogin(){
		return "login";
	}
	
}
