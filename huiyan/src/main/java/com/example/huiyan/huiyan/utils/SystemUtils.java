package com.example.huiyan.huiyan.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


public class SystemUtils {

	public static byte[] httpPost(String url, byte[] sdata, Map<String, Object> requestParam, boolean printError){
		OutputStream out = null;
		try {			
			if(url==null || url.length()==0){
				return null;
			}		
			
			URL uRL = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) uRL.openConnection();

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "binary");

			if(null != requestParam && requestParam.size() > 0){
				for(String key : requestParam.keySet()){
					conn.addRequestProperty(key, requestParam.get(key) + "");
				}
			}

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			if(null != sdata){
				out = conn.getOutputStream();
				out.write(sdata);
				out.flush();
			}
			conn.setReadTimeout(30000);
			conn.connect();
			if(out != null){
				out.close();
			}

			byte[] data = stream2Bytes(conn.getInputStream());
			
			if(null != data){
				return data;
			}
		} catch (Throwable e) {
			if(printError){
				e.printStackTrace();
			}
		}
		return null;
	}

	public static byte[] httpGet(String url, byte[] sdata, boolean printError){
		OutputStream out = null;
		try {
			URL uRL = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) uRL.openConnection();
				
			if(null != sdata){				
				conn.setDoOutput(true); 
				conn.setDoInput(true); 
				conn.setUseCaches(false); 
				
				out = conn.getOutputStream();
				out.write(sdata);
				out.flush();
			}
			
			conn.setReadTimeout(60000);
			
			byte[] data = stream2Bytes(conn.getInputStream());
			if(null != out){
				out.close();
			}
			
			if(null != data){
				return data;  
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 休眠
	 * @param time
	 */
	public static void sleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			System.out.println("sleep error"+ e.getMessage());
		}
	}
	/**
	 * 
	 * @param in
	 * @return
	 */
	public static byte[] stream2Bytes(InputStream in){
		
		byte[] bytes = null;
		ByteArrayOutputStream bufferStream=null;
		try {
			bufferStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[2048];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {					
    			bufferStream.write(buffer, 0, len);
    		}
    		if(bufferStream.toByteArray().length>0){    			
    			bytes = bufferStream.toByteArray();
    		}
    	//	bufferStream.close();
    		if(in!=null){
				in.close();
			}
    		return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(bufferStream);
		}
		return null;
	}
	/**
	 *  解决ByteArrayOutputStream OOM
	 * @param in
	 * @return
	 */
	public static byte[] stream2Bytes(InputStream in,long length){

		byte[] bytes = null;
		ByteArrayOutputStream bufferStream=null;
		try {
			bufferStream = new ByteArrayOutputStream((int)length);
			byte[] buffer = new byte[2048];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				bufferStream.write(buffer, 0, len);
			}
			if(bufferStream.toByteArray().length>0){
				bytes = bufferStream.toByteArray();
			}
			//	bufferStream.close();
			if(in!=null){
				in.close();
			}
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(bufferStream);
		}
		return null;
	}
	/**
	 * SHA1加密
	 *
	 * @param decript
	 * @return
	 */
	public static String encryptSHA1(String decript) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes(StandardCharsets.UTF_8));
			byte[] messageDigest = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (Exception e) {
			return "";
		}
	}

	public static String getMd5Hex(InputStream inputStream) {

		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		try {
			byte[] buffer = new byte[2048];
			int read = inputStream.read(buffer);
			while (read > -1) {
				// 计算MD5
				digest.update(buffer, 0, read);

				read = inputStream.read(buffer);
			}
		} catch (Exception e) {

		}

		return Hex.encodeHexString(digest.digest());
	}
}