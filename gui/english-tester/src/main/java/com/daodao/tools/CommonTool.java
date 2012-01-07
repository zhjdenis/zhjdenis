package com.daodao.tools;

import java.io.UnsupportedEncodingException;

public class CommonTool {

	public static String removeChinese(String inputStr)
			throws UnsupportedEncodingException {
		if (inputStr == null || inputStr.trim().equals("")) {

		}
		char[] chars = inputStr.toCharArray();
		for (int index = 0; index < chars.length; index++) {
			byte[] bytes = ("" + chars[index]).getBytes("UTF-8");
			if (bytes.length >= 2) {
				int[] ints = new int[2];
				ints[0] = bytes[0] & 0xff;
				ints[1] = bytes[1] & 0xff;
				if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40
						&& ints[1] <= 0xFE) {
					return inputStr.substring(0, index).trim();
				}
			}
		}
		return inputStr;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "test 测试";
		System.out.println(removeChinese(str));
	}
}
