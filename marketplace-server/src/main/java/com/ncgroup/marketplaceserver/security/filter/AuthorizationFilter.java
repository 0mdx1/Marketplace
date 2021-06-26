package com.ncgroup.marketplaceserver.security.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ncgroup.marketplaceserver.security.constants.JwtConstants;
import com.ncgroup.marketplaceserver.security.util.JwtProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {
	private JwtProvider jwtProvider;

	public AuthorizationFilter(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
			// Allow all OPTIONS methods as they are used to retrieve info about server
			response.setStatus(OK.value());
		} else {
			// log.info("1");
			String authorizationHeader = request.getHeader(AUTHORIZATION);
			if (authorizationHeader == null || !authorizationHeader.startsWith(JwtConstants.TOKEN_PREFIX)) {
				// There are no appropriate tokens
				filterChain.doFilter(request, response);
				return;
			}
			// Remove prefix to get the actual token value
			String token = authorizationHeader.substring(JwtConstants.TOKEN_PREFIX.length());
			String username = jwtProvider.getSubject(token);
			if (jwtProvider.isTokenValid(username, token)) {
				List<GrantedAuthority> authorities = jwtProvider.getAuthorities(token);
				Authentication authentication = jwtProvider.getAuthentication(username, authorities, request);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				SecurityContextHolder.clearContext();
			}
		}
		filterChain.doFilter(request, response);
	}
}
