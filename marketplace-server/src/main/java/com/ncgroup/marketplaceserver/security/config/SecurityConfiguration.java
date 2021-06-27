package com.ncgroup.marketplaceserver.security.config;

import javax.servlet.Filter;

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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ncgroup.marketplaceserver.security.filter.AuthorizationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{
	private AuthorizationFilter authorizationFilter;
    //private JwtAccessDeniedHandler jwtAccessDeniedHandler;
    //private AuthenticationFilter authenticationFilter;
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityConfiguration(AuthorizationFilter authorizationFilter,
                                 //JwtAccessDeniedHandler jwtAccessDeniedHandler,
                                 //AuthenticationFilter authenticationFilter,
                                 @Qualifier("userDetailsService")UserDetailsService userDetailsService,
                                 BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authorizationFilter = authorizationFilter;
        //this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        //this.authenticationFilter = authenticationFilter;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
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
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("*");
    }
}

