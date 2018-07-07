package com.hll.web.service;

public interface MailService {
	
	boolean sendEmail(String toAddress, String subject, String msgBody);
}
