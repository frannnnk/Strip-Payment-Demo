package com.frank.demo.model;

import java.util.List;


public class SSOUser {

    String loginName;
    String cpUserId;
    String languageUDBA;
    String languageSimplified;

    List<String> roles;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getCpUserId() {
        return cpUserId;
    }

    public void setCpUserId(String cpUserId) {
        this.cpUserId = cpUserId;
    }

    public String getLanguageUDBA() {
        return languageUDBA;
    }

    public void setLanguageUDBA(String languageUDBA) {
        this.languageUDBA = languageUDBA;
        if (languageUDBA.equalsIgnoreCase("en")) {
            setLanguageSimplified("en");
        } else if (languageUDBA.equalsIgnoreCase("zh_tw")) {
            setLanguageSimplified("tc");
        } else if (languageUDBA.equalsIgnoreCase("zh_cn")) {
            setLanguageSimplified("sc");
        }
    }

    public String getLanguageSimplified() {
        return languageSimplified;
    }

    public void setLanguageSimplified(String languageSimplified) {
        this.languageSimplified = languageSimplified;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
