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
		// ����debug���ԣ��Ա��ڿ���̨�鿴
		properties.setProperty("mail.debug", "true");
		// �����ʼ�������������
		properties.setProperty("mail.host", "smtp.qq.com");
		// ���ͷ�������Ҫ�����֤
		properties.setProperty("mail.smtp.auth", "true");
		// �����ʼ�Э������
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.ssl.enable", "true");
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("513993406", "pcxybvjlbsjbbhhf");
			}
		});
		//�����ʼ�����
		Message message = new MimeMessage(session);
		//���÷�����
		message.setFrom(new InternetAddress("513993406@qq.com"));
		//�����ռ���
		message.setRecipient(RecipientType.TO, new InternetAddress(to));
		//�����ʼ�����
		message.setSubject("�����ʼ�");
		//�����ʼ�����
		message.setContent("��ӭ��ע��,�˺�ע��������������(һ������Ч):<h3><a href='http://localhost:8080/java_email_regiest/activation.do?code="+code+"' >http://localhost:8080/java_email_regiest/activation.do?code="+code+"</a></h3>����ظ������䡣", "text/html;charset=UTF-8");
		//�����ʼ�
		Transport.send(message);
	}
}
