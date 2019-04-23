package com.wenlong.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	public static void sendMail(String to, String code) throws Exception{
		Properties properties = System.getProperties();
		// 开启debug调试，以便在控制台查看
		properties.setProperty("mail.debug", "true");
		// 设置邮件服务器主机名
		properties.setProperty("mail.host", "smtp.qq.com");
		// 发送服务器需要身份验证
		properties.setProperty("mail.smtp.auth", "true");
		// 发送邮件协议名称
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.ssl.enable", "true");
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("513993406", "pcxybvjlbsjbbhhf");
			}
		});
		//创建邮件对象
		Message message = new MimeMessage(session);
		//设置发件人
		message.setFrom(new InternetAddress("513993406@qq.com"));
		//设置收件人
		message.setRecipient(RecipientType.TO, new InternetAddress(to));
		//设置邮件主题
		message.setSubject("激活邮件");
		//设置邮件正文
		message.setContent("欢迎您注册,账号注册请点击以下链接(一分钟有效):<h3><a href='http://localhost:8080/java_email_regiest/activation.do?code="+code+"' >http://localhost:8080/java_email_regiest/activation.do?code="+code+"</a></h3>请勿回复此邮箱。", "text/html;charset=UTF-8");
		//发送邮件
		Transport.send(message);
	}
}
