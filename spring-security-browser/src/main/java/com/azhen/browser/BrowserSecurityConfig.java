package com.azhen.browser;

import com.azhen.browser.authentication.MyAuthenctiationFailureHandler;
import com.azhen.browser.authentication.MyAuthenticationSuccessHandler;
import com.azhen.core.properties.SecurityProperties;
import com.azhen.core.validate.core.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

import static com.azhen.core.properties.SecurityConstants.*;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private MyAuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private MyAuthenctiationFailureHandler authenctiationFailureHandler;
	@Autowired
	private ValidateCodeFilter validateCodeFilter;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private DataSource dataSource;

	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		tokenRepository.setCreateTableOnStartup(true);
		return tokenRepository;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
				.formLogin()
					.loginPage(DEFAULT_UNAUTHENTICATION_URL)
					.loginProcessingUrl(DEFAULT_LOGIN_PROCESSING_URL_FORM)
					.successHandler(authenticationSuccessHandler)
					.failureHandler(authenctiationFailureHandler)
					.and()
				.rememberMe()
					.tokenRepository(persistentTokenRepository())
					.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
					.userDetailsService(userDetailsService)
				.and()
				.authorizeRequests()
				.antMatchers(DEFAULT_UNAUTHENTICATION_URL, securityProperties.getBrowser().getLoginPage(),
						DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*").permitAll()
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
