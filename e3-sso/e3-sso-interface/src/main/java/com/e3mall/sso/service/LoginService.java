package com.e3mall.sso.service;

import com.e3mall.common.untils.E3Result;

public interface LoginService {
	
	public E3Result login(String username,String password);
	
}
