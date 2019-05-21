package com.agile.demo.common.authentication;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author liuyi
 * @date 2019/5/15
 *
 * token
 */
public class OAuthToken implements AuthenticationToken {

    private String token;

    public OAuthToken(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
