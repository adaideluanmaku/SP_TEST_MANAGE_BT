package com.ch.sys;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

//kettle数据库配置加密
public class Crypt_Kettyle {
	private static final int RADIX = 16;
	private static final String SEED = "0933910847463829827159347601486730416058";
	public static final String PASSWORD_ENCRYPTED_PREFIX = "Encrypted ";

	public static final String encryptPassword(String password) {
		if (password == null)
			return "";
		if (password.length() == 0)
			return "";

		BigInteger bi_passwd = new BigInteger(password.getBytes());

		BigInteger bi_r0 = new BigInteger(SEED);
		BigInteger bi_r1 = bi_r0.xor(bi_passwd);

		return bi_r1.toString(RADIX);
	}

	public static final String decryptPassword(String encrypted) {
		if (encrypted == null)
			return "";
		if (encrypted.length() == 0)
			return "";

		BigInteger bi_confuse = new BigInteger(SEED);

		try {
			BigInteger bi_r1 = new BigInteger(encrypted, RADIX);
			BigInteger bi_r0 = bi_r1.xor(bi_confuse);

			return new String(bi_r0.toByteArray());
		} catch (Exception e) {
			return "";
		}
	}
	
	public static void main(String args[]){
		Crypt_Kettyle password=new Crypt_Kettyle();
		System.out.println("加密"+password.encryptPassword("zfxmz"));
		System.out.println("解密"+password.decryptPassword("2be98afc86aa7f2e4cb79ce6ad89fb7c0"));//2be98afc86aa7f2e4cb79ce6ad88aa2c0
	}
}
