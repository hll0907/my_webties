package com.hll.web.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.stereotype.Service;

import com.hll.web.service.MailService;
import com.hll.web.util.MailUtil;

@Service
public class MailServiceImpl implements MailService {

	@Override
	public boolean sendEmail(String toAddress, String subject, String msgBody) {
		int sendMailEntity = 0;
		msgBody = "您好！<br>"
				+ "您修改帐户密码所需要的验证码：<b>" + msgBody +"</b>,一定要牢记哦。半个小时内失效。";
		try {
			sendMailEntity = MailUtil.sendMailEntity(toAddress, subject, msgBody);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		if (sendMailEntity == 1) {
			return true;
		}
		return false;
	}

}