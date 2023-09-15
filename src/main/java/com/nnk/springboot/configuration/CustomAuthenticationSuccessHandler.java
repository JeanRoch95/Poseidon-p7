package com.nnk.springboot.configuration;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom Authentication Success Handler.
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    /**
     * Handles successful authentication.
     *
     * @param request        HttpServletRequest object
     * @param response       HttpServletResponse object
     * @param authentication Authentication object
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    /**
     * Handles the redirection to the target URL.
     *
     * @param request        HttpServletRequest object
     * @param response       HttpServletResponse object
     * @param authentication Authentication object
     * @throws IOException if an I/O error occurs
     */
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, authentication);
        if (response.isCommitted()) {
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * Retrieves the value of a specific cookie.
     *
     * @param request    HttpServletRequest object
     * @param cookieName Name of the cookie
     * @return String representing the cookie value
     */
    public String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Determines the target URL based on the role of the authenticated user.
     *
     * @param request        HttpServletRequest object
     * @param authentication Authentication object
     * @return String representing the target URL
     */
    protected String determineTargetUrl(HttpServletRequest request, final Authentication authentication) {
        String loginFor = getCookieValue(request, "loginFor");

        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("USER", "/bidList/list");
        roleTargetUrlMap.put("ADMIN", "/user/list");

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();

            if (roleTargetUrlMap.containsKey(authorityName)) {

                if ("ADMIN".equals(loginFor)) {
                    if ("ADMIN".equals(authorityName)) {
                        return "/user/list";
                    } else {
                        return "/app/error/connection";
                    }
                } else {
                    return roleTargetUrlMap.get(authorityName);
                }
            }
        }
        throw new IllegalStateException();
    }

    /**
     * Clears authentication attributes from the session.
     *
     * @param request HttpServletRequest object
     */
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}