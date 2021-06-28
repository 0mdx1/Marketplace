package com.ncgroup.marketplaceserver.security.config;

import javax.servlet.Filter;

import com.ncgroup.marketplaceserver.exception.filter.FilterChainExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ncgroup.marketplaceserver.security.filter.AuthorizationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	private AuthorizationFilter authorizationFilter;
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private FilterChainExceptionHandler filterChainExceptionHandler;

    @Autowired
    public SecurityConfiguration(
            AuthorizationFilter authorizationFilter,
            @Qualifier("userDetailsService")UserDetailsService userDetailsService,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            FilterChainExceptionHandler filterChainExceptionHandler
    ) {
        this.authorizationFilter = authorizationFilter;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.filterChainExceptionHandler = filterChainExceptionHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(filterChainExceptionHandler, CorsFilter.class)
            .csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
                .antMatchers("/api/media/").hasAnyRole("ADMIN","PRODUCT_MANAGER")
                .antMatchers("/api/shopping-cart/**")
                    .hasRole("USER")
                .antMatchers(HttpMethod.PATCH, "/api/courier/**", "api/manager/**")
                    .hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/manager")
                    .hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/courier")
                    .hasRole("ADMIN")
                .antMatchers("/api/orders/incoming", "/api/orders/history", "/api/orders/{id:[\\d+]}/courierinfo")
                	.hasRole("USER")
                .antMatchers("/api/orders/{id:[\\d+]}")
                	.hasAnyRole("USER", "COURIER")
                .antMatchers(HttpMethod.GET, "/api/media/**")
                    .hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/orders/**")
                	.hasRole("COURIER")
                .antMatchers("/api/userinfo")
                	.hasAnyRole("USER", "ADMIN", "COURIER", "PRODUCT_MANAGER")
                .and()
                //.exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
                //.authenticationEntryPoint(authenticationFilter)
                //.and()
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
    @Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/swagger/swagger-ui/**", "/v3/api-docs/**")
			.antMatchers("/api/register", "/api/login","/api/confirm-account","/api/reset-password")
			.antMatchers("/api/confirm-passreset/**", "/api/confirm-passcreate/**", "/api/setnewpassword/**")
			.antMatchers("/api/shopping-cart/validate/")
			.antMatchers("/api/orders/freeslots", "/api/orders/userinfo");
	}

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public RegistrationBean jwtAuthFilterRegister(AuthorizationFilter filter) {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }
}

