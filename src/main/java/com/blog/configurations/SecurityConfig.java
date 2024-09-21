package com.blog.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.JWT.JWTAuthenticationEntryPoint;
import com.blog.JWT.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableWebMvc
public class SecurityConfig {
	
	public  static final String [] PUBLIC_URLS= {"/auth/**",
																			"/v3/api-docs",
																			"/v2/api-docs",
																			"/swagger-resources/**",
																			"/swagger-ui/**",
																			"/webjars/**"
	};
	
	@Autowired
	private JWTAuthenticationEntryPoint  jwtAuthenticationEntryPoint;
	
	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public UserDetailsService uds() {
		return new DBuserdetailservice();
	}
	
	@Bean
	public PasswordEncoder pa() {	
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider da() {
		DaoAuthenticationProvider da=new DaoAuthenticationProvider();
		da.setUserDetailsService(uds());
		da.setPasswordEncoder(pa());
		return da;
	}
	
	//for JWT we use AuthenticationManager 
	@Bean
	public AuthenticationManager am(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain ss(HttpSecurity http) throws Exception {
		http.csrf(
				        c -> c.disable()
				       )
		        .authorizeHttpRequests( a-> a.requestMatchers(PUBLIC_URLS)
        											     		.permitAll()
        											  
		    		   											.requestMatchers(HttpMethod.GET)
		    		   											.permitAll()
		    		   											.anyRequest()
		    		                                           .authenticated()    		                                           
		    		                                  )
		        .exceptionHandling(
		    		   e->e.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		    		   )
		        .sessionManagement(
		    		   s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		    		  );
		       
		       http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		       
		      // .httpBasic(Customizer.withDefaults());   because of JWT we commented it
		       /*we have use this as we are testing in postman and for authentication it is giving form and 
		           we cant use formbased login in postman so by using this it will give javascript based login 
		            so in postman in Authorization->basic auth->we can pass username and pass
		        */ 
	           
		
		return http.build();
	}
}
