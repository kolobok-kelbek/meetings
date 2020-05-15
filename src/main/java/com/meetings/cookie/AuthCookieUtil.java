package com.meetings.cookie;

import com.meetings.configuration.SecurityConstants;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.util.WebUtils;

public class AuthCookieUtil {
  public static void create(HttpServletResponse httpServletResponse, String name, String value) {
    Cookie cookie = new Cookie(name, value);
    cookie.setHttpOnly(true);
    cookie.setMaxAge(SecurityConstants.TOKEN_LIFETIME_SECOND);
    cookie.setDomain(SecurityConstants.COOKIE_PERMISSIONS_DOMAIN);
    cookie.setPath(SecurityConstants.COOKIE_PERMISSIONS_PATH);
    httpServletResponse.addCookie(cookie);
  }

  public static void clear(HttpServletResponse httpServletResponse, String name) {
    Cookie cookie = new Cookie(name, null);
    cookie.setPath(SecurityConstants.COOKIE_PERMISSIONS_PATH);
    cookie.setHttpOnly(true);
    cookie.setMaxAge(0);
    cookie.setDomain(SecurityConstants.COOKIE_PERMISSIONS_DOMAIN);
    httpServletResponse.addCookie(cookie);
  }

  public static String getValue(HttpServletRequest httpServletRequest, String name) {
    Cookie cookie = WebUtils.getCookie(httpServletRequest, name);

    return cookie != null ? cookie.getValue() : null;
  }
}
