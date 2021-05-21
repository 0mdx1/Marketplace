package com.ncgroup.marketplaceserver.security.util;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ncgroup.marketplaceserver.exception.constants.ExceptionMessage;
import com.ncgroup.marketplaceserver.security.constants.JwtConstants;
import com.ncgroup.marketplaceserver.security.model.UserPrincipal;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtProvider {
	
	private JwtKeyProvider jwtKeyProvider;
	
	@Autowired
	public JwtProvider(JwtKeyProvider jwtKeyProvider) {
		this.jwtKeyProvider = jwtKeyProvider;
	}
	
	public String generateJwtToken(UserPrincipal userPrincipal) {
		List<String> claims = getClaims(userPrincipal); 
		return JWT.create()
				.withIssuedAt(new Date())
				.withSubject(userPrincipal.getUsername())
				.withClaim(JwtConstants.AUTHORITIES, claims)
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION_TIME_MS))
				.sign(Algorithm.HMAC512(jwtKeyProvider.getSecret().getBytes()));
	}
	
	public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken token = 
				new UsernamePasswordAuthenticationToken(username, null, authorities);
		token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return token;
	}
	
	/*Checks whether username is not null and token is not expired*/
	public boolean isTokenValid(String username, String token) {
		JWTVerifier verifier = getJWTVerifier();
		return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);
	}
	
	//gets the username
	public String getSubject(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }
	
	/*Get granted authorities from token claim in order to use them during security process*/
	public List<GrantedAuthority> getAuthorities(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(JwtConstants.AUTHORITIES).asList(String.class)
        		.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
	
	private boolean isTokenExpired(JWTVerifier verifier, String token) {
		
		return verifier.verify(token).getExpiresAt().before(new Date());
	}

	private List<String> getClaims(UserPrincipal userPrincipal) {
		return userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
	}
	

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(jwtKeyProvider.getSecret());
            verifier = JWT.require(algorithm).build();
        }catch (JWTVerificationException exception) {
        	log.debug("Token cannot be verified");
            throw new JWTVerificationException(ExceptionMessage.TOKEN_NOT_VERIFIED);
        }
        return verifier;
    }

}
