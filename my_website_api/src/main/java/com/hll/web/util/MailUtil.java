package com.hll.web.util;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import com.sun.mail.util.MailSSLSocketFactory;

public class MailUtil {
	private static Logger logger = Logger.getLogger(MailUtil.class);
	// --------------参数---------------------
	public static final String FROM = "2421564216@qq.com";// 发件人的email
	public static final String PWD = "eqdmxjiwhqikdhhi";// 发件人密码--邮箱密码

	public static int sendMailEntity(String to, String title, String content)
			throws AddressException, MessagingException {

		Properties props = new Properties(); // 可以加载一个配置文件
		// 设置邮件服务器主机名
		props.setProperty("mail.host", "smtp.qq.com");// 指定邮件服务器，默认端口 25
		// 发送服务器需要身份验证
		props.setProperty("mail.smtp.auth", "true");// 要采用指定用户名密码的方式去认证
		// 发送邮件协议名称
		props.setProperty("mail.transport.protocol", "smtp");

		// 开启SSL加密，否则会失败
		MailSSLSocketFactory sf = null;
		try {
			sf = new MailSSLSocketFactory();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sf.setTrustAllHosts(true);
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.socketFactory", sf);

		// 根据属性新建一个邮件会话
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication(FROM, PWD);
			}
		});
		session.setDebug(true); // 有他会打印一些调试信息。
		MimeMessage message = new MimeMessage(session);// 由邮件会话新建一个消息对象
		message.setFrom(new InternetAddress(FROM));// 设置发件人的地址
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));// 设置收件人,并设置其接收类型为TO
		message.setSubject(title, "UTF-8");// 设置标题
		// 设置信件内容
		// message.setText(content,"UTF-8"); //发送 纯文本 邮件 todo
		message.setContent(content, "text/html;charset=utf-8"); // 发送HTML邮件，内容样式比较丰富
		message.setSentDate(new Date());// 设置发信时间
		// message.saveChanges();//存储邮件信息
		// 发送邮件
		Transport.send(message);
		logger.debug("Transport.send");
		/*
		 * //发送邮件 (二) Transport transport = session.getTransport(SMTP); //Transport
		 * transport = session.getTransport(); transport.connect(FROM, PWD);
		 * transport.sendMessage(message,
		 * message.getAllRecipients());//发送邮件,其中第二个参数是所有已设好的收件人地址 transport.close();
		 */
		return 1;
	}

}