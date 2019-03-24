package com.azhen.browser;

import com.azhen.browser.authentication.MyAuthenctiationFailureHandler;
import com.azhen.browser.authentication.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.azhen.core.properties.SecurityProperties;

import static com.azhen.core.properties.SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM;
import static com.azhen.core.properties.SecurityConstants.DEFAULT_UNAUTHENTICATION_URL;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private MyAuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private MyAuthenctiationFailureHandler authenctiationFailureHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				.loginPage(DEFAULT_UNAUTHENTICATION_URL)
				.loginProcessingUrl(DEFAULT_LOGIN_PROCESSING_URL_FORM)
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenctiationFailureHandler)
				.and()
				.authorizeRequests()
				.antMatchers(DEFAULT_UNAUTHENTICATION_URL, securityProperties.getBrowser().getLoginPage()).permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.csrf().disable();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


}
