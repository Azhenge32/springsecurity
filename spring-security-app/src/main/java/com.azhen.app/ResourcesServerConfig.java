package com.azhen.app;

import com.azhen.core.authentication.AbstractChannelSecurityConfig;
import com.azhen.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.azhen.core.properties.SecurityConstants;
import com.azhen.core.properties.SecurityProperties;
import com.azhen.core.validate.core.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableResourceServer
public class ResourcesServerConfig extends AbstractChannelSecurityConfig {
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        applyPasswordAuthenticationConfig(http);
        http.apply(validateCodeSecurityConfig)
                .and()
            .apply(smsCodeAuthenticationSecurityConfig)
            .and()
            .authorizeRequests()
            .antMatchers(
                    SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                    SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                    securityProperties.getBrowser().getLoginPage(),
                    SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                    securityProperties.getBrowser().getSignUpUrl(),
                    securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".json",
                    securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".html",
                    securityProperties.getBrowser().getLogoutUrl(),
                    "/user/regist", "/leave")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable();
    }
}
