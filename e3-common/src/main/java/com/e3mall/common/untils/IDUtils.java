package com.e3mall.common.untils;

import java.util.Random;


public class IDUtils {
	
	public static long getItemId() {
		long mm = System.currentTimeMillis();
		Random random = new Random();
		int randNum = random.nextInt(99);
		String tid = mm + String.format("%02d", randNum);
		Long id = new Long(tid);
		return id;
	}
	
	public static void main(String[] args) {
		System.out.println(getItemId());
	}
	
}
