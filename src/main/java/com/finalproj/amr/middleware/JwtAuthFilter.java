package com.finalproj.amr.middleware;

import com.finalproj.amr.jsonObject.UserJwt;
import com.finalproj.amr.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebFilter(filterName = "JWTFilter", urlPatterns = "/api/*")
public class JwtAuthFilter implements Filter {
    private final JwtUtils jwtUtils;

    public JwtAuthFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String authCookie = null;
        if (httpRequest.getCookies() != null) {
            for (Cookie cookie : httpRequest.getCookies()) {
                if ("access-token".equals(cookie.getName())) { // <-- your cookie name
                    authCookie = cookie.getValue();
                }
            }
        }
        if(authCookie!=null){
            if(jwtUtils.validateToken(authCookie)){
                //okay, user is legit
                HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                UserJwt user = jwtUtils.getUserJwt(authCookie);
                httpRequest.setAttribute("user", user);
                filterChain.doFilter(httpRequest,httpResponse);
            }else{
            //bad user
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");

            }
        }else{
        //user not signed in
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Cookie is missing");

        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
