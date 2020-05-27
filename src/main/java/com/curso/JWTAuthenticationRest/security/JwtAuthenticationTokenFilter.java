package com.curso.JWTAuthenticationRest.security;

import com.curso.JWTAuthenticationRest.constants.Constants;
import com.curso.JWTAuthenticationRest.model.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.curso.JWTAuthenticationRest.constants.Constants.AUTHORIZATION_HEADER;
import static com.curso.JWTAuthenticationRest.constants.Constants.BEARER_TOKEN;

public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

    public JwtAuthenticationTokenFilter() {
        super("/transaction/**");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {

        String header  = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        String token=null;
        if (header!=null){
            token=header.substring(7);
        }else{
            Cookie[] cookies = httpServletRequest.getCookies();
            if( cookies == null || cookies.length < 1 ) {
                throw new AuthenticationServiceException( "Invalid Token" );
            }

            Cookie sessionCookie = null;
            for( Cookie cookie : cookies ) {
                if( ( "token" ).equals( cookie.getName() ) ) {
                    sessionCookie = cookie;
                }
            }
            token=sessionCookie.getValue();
        }

        String authenticationToken=token;
        JwtAuthenticationToken auth_token = new JwtAuthenticationToken(authenticationToken);
        return getAuthenticationManager().authenticate(auth_token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
