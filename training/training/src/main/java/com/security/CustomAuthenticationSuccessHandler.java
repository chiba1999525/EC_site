package com.security;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                        HttpServletResponse response, 
                                        Authentication authentication) throws IOException, ServletException {
        // ユーザーのロールを取得
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // ロールに基づいてリダイレクト先を決定
        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/top");
        } else if (roles.contains("ROLE_USER")) {
            response.sendRedirect("/user/top");
        } else {
            response.sendRedirect("/"); // デフォルトのリダイレクト先
        }
    }
}

