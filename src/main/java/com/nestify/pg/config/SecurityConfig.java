package com.nestify.pg.config;

import com.nestify.pg.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtUtil jwtUtil;

	public SecurityConfig(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/auth/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/pg-listings/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/pg-listings/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/pg-listings/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/pg-listings/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/rooms/**", "/api/tenants/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/rooms/**", "/api/tenants/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/rooms/**", "/api/tenants/**").hasRole("ADMIN")
						.anyRequest().authenticated())
				.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
					throws jakarta.servlet.ServletException, IOException {

				String header = request.getHeader("Authorization");

				if (header != null && header.startsWith("Bearer ")) {
					String token = header.substring(7);
					if (jwtUtil.validateToken(token)) {
						String username = jwtUtil.extractUsername(token);
						String role = jwtUtil.extractRole(token);
						UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username,
								null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));
						SecurityContextHolder.getContext().setAuthentication(auth);
					}
				}
				chain.doFilter(request, response);
			}
		};
	}
}