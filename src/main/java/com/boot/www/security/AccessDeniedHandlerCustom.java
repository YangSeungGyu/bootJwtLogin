package com.boot.www.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AccessDeniedHandlerCustom implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    	log.debug(" 이건 뭐냐55555555555555555555555555");
        log.warn("[AccessDeniedHandlerCustom] Forbidden error : {}", accessDeniedException.getMessage());
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}