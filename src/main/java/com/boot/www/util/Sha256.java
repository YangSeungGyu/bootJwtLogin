package com.boot.www.util;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

public class Sha256 {
	/*
	단방향 암호화
	*/
	public static String encoder(String str) {
		 String hash = null;
		 try {
		     String secret = "mySecretKey";

		     Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		     SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
		     sha256_HMAC.init(secret_key);

		      hash = Base64.encodeBase64String(sha256_HMAC.doFinal(str.getBytes()));
		     System.out.println(hash);
		    }
		    catch (Exception e){
		     System.out.println("Error");
		    }
		return hash;
	}
	

}
