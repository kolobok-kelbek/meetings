package com.meetings.filter;

import com.meetings.configuration.SecurityConstants;
import com.meetings.converter.SimpleConverter;
import com.meetings.converter.factory.ConverterFactory;
import com.meetings.cookie.AuthCookieUtil;
import com.meetings.entity.User;
import com.meetings.model.input.Sign;
import com.meetings.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.io.InputStream;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  private final UserService userService;

  private final PasswordEncoder passwordEncoder;

  private final SimpleConverter<InputStream, Sign> converter;

  public JwtAuthenticationFilter(
      AuthenticationManager authenticationManager,
      UserService userService,
      ConverterFactory converterFactory,
      PasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.converter = converterFactory.createSimpleConverter();

    setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
  }

  @SneakyThrows
  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) {

    Sign sign = converter.convert(request.getInputStream(), Sign.class);

    User user = userService.findUserByUsername(sign.getUsername());

    if (passwordEncoder.matches(sign.getPassword(), user.getPassword())) {

      var authenticationToken =
          new UsernamePasswordAuthenticationToken(sign.getUsername(), sign.getPassword());

      return authenticationManager.authenticate(authenticationToken);
    }

    AuthCookieUtil.clear(response, SecurityConstants.TOKEN_COOKIE);

    return null;
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain,
      Authentication authentication) {
    var user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

    var roles =
        user.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    var signingKey = SecurityConstants.JWT_SECRET.getBytes();

    var token =
        Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
            .setHeaderParam(SecurityConstants.TOKEN_PARAM_KEY_TYPE, SecurityConstants.TOKEN_TYPE)
            .setIssuer(SecurityConstants.TOKEN_ISSUER)
            .setAudience(SecurityConstants.TOKEN_AUDIENCE)
            .setSubject(user.getUsername()) // user phone
            .setExpiration(
                new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_LIFETIME_MILLISECOND))
            .claim(SecurityConstants.TOKEN_PARAM_KEY_ROLES, roles)
            .compact();

    AuthCookieUtil.create(
        response, SecurityConstants.TOKEN_COOKIE, SecurityConstants.TOKEN_PREFIX + token);
  }
}
