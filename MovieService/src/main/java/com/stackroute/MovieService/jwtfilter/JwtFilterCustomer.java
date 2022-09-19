package com.stackroute.MovieService.jwtfilter;

import io.jsonwebtoken.*;
import io.swagger.models.HttpMethod;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilterCustomer extends GenericFilterBean {
//
//    public static final String TOKEN_SECRET = "oscarmovie";
//
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpResponse.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS");
//        httpResponse.setHeader("Access-Control-Allow-Headers", "*");
//
//        if (httpRequest.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
//            chain.doFilter(httpRequest, httpResponse);
//        } else {
//
//            String authHeader = httpRequest.getHeader("Authorization");
//
//            System.out.println("Token is " + authHeader);
//            String myToken = authHeader.substring(7);
//
//            try {
//                String userType = Jwts.parser()
//                        .setSigningKey("oscarmovie")
//                        .parseClaimsJws(myToken)
//                        .getBody()
//                        .getSubject();
//
//                if (!userType.equalsIgnoreCase("customer")) {
//                    throw new ServletException("The user type is incorrect");
//                }
//
//            } catch (SignatureException sign) {
//                throw new ServletException("Signature mismatch");
//
//            } catch (MalformedJwtException exception) {
//                throw new ServletException("Token is modified by unauthorized user");
//            }
//        }
//        chain.doFilter(httpRequest, httpResponse);

    }
}
