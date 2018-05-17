package com.e3mall.sso.service;

import com.e3mall.common.untils.E3Result;

public interface TokenService {
	public E3Result getUserByToken(String token);
}
