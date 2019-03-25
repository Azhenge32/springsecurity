/**
 * 
 */
package com.azhen.core.validate.core.sms;


public interface SmsCodeSender {
	
	void send(String mobile, String code);

}
