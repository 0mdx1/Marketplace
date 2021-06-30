package com.ncgroup.marketplaceserver.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import com.ncgroup.marketplaceserver.domain.ApiError;
import com.ncgroup.marketplaceserver.exception.constants.ExceptionType;
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
public class AuthenticationFilter extends Http403ForbiddenEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
    		throws IOException {
        ApiError apiError = new ApiError(ExceptionType.UNAUTHENTICATED,"You need to login to access this page");
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(FORBIDDEN.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, apiError);
        outputStream.flush();
    }
}
