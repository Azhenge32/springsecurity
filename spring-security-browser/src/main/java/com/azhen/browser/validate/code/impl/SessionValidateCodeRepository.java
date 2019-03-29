package com.azhen.browser.validate.code.impl;

import com.azhen.core.validate.core.ValidateCode;
import com.azhen.core.validate.core.ValidateCodeRepository;
import com.azhen.core.validate.core.ValidateCodeType;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {
    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 构建验证码放入session时的key
     * @param type
     * @return
     */
    private String getSessionKey(ValidateCodeType type) {
        return SESSION_KEY_PREFIX + type.toString().toUpperCase();
    }
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
        sessionStrategy.setAttribute(request, getSessionKey(type) ,code);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        return (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(type));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        sessionStrategy.removeAttribute(request, getSessionKey(type));
    }
}
