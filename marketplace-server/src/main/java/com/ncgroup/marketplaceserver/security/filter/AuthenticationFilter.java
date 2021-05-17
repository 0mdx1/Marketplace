package com.ncgroup.marketplaceserver.security.filter;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncgroup.marketplaceserver.domain.HttpResponse;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/*
 * Need this filter to react on failed authentication process
 */
@Component
@Slf4j
public class AuthenticationFilter extends Http403ForbiddenEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) 
    		throws IOException {
    	//log.info(exception.toString());
        HttpResponse httpResponse = new HttpResponse(FORBIDDEN.value(), 
        		FORBIDDEN, FORBIDDEN.getReasonPhrase().toUpperCase(), "You need to login to access this page");
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(FORBIDDEN.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, httpResponse);
        outputStream.flush();
    }
}
