package com.azhen.core.properties;

public class BrowserProperties {
    private String signUpUrl = "/imooc-signUp.html";
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }
}
