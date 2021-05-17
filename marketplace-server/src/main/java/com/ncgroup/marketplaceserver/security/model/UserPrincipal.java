package com.ncgroup.marketplaceserver.security.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ncgroup.marketplaceserver.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserPrincipal implements UserDetails {	
	private User user;
	
	public UserPrincipal(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		log.info(user.toString());
		return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
		/*return StreamSupport.stream(person.getAuthorities().spliterator(), false)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());*/
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}

}
