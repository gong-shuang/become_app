package com.gs.learn.network.util;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPUtil {
	private static String mUurl;
	private static String mPort;
	private static String mUsername;
	private static String mPassword;
	
	public static void setUser(String url, String port, String username, String password) {
		mUurl = url;
		mPort = port;
		mUsername = username;
		mPassword = password;
	}
	
	public static String upload(String remotePath, String filePath, String fileName) {
		FTPClient ftpClient = new FTPClient();
		String result = "SUCC";
		try {
			ftpClient.connect(mUurl, Integer.parseInt(mPort));
			boolean loginResult = ftpClient.login(mUsername, mPassword);
			int returnCode = ftpClient.getReplyCode();
			if (loginResult && FTPReply.isPositiveCompletion(returnCode)) { //登录成功
				ftpClient.makeDirectory(remotePath);
				// 设置上传目录
				ftpClient.changeWorkingDirectory(remotePath);
				ftpClient.setBufferSize(1024);
				ftpClient.setControlEncoding("UTF-8");
				ftpClient.enterLocalPassiveMode();
				FileInputStream fis = new FileInputStream(filePath + fileName);
				ftpClient.storeFile(fileName, fis);
			} else { //登录失败
				result = "FAIL";
			}
		} catch (IOException e) {
			e.printStackTrace();
			result = "FTP客户端出错！" + e.getMessage();
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				result = "关闭FTP连接发生异常！" + e.getMessage();
			}
		}
		return result;
	}
}
