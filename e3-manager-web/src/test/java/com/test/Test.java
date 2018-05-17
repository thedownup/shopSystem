package com.test;

import static org.junit.Assert.*;

import java.net.URLDecoder;

public class Test {

	@SuppressWarnings("deprecation")
	@org.junit.Test
	public void test() {
		String path = this.getClass().getResource("/").getPath();
		String decode = URLDecoder.decode(path);
		System.out.println(decode);
	}

}
