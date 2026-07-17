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

						// PG listings: public browsing, admin-only writes
						.requestMatchers(HttpMethod.GET, "/api/pg-listings/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/pg-listings/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/pg-listings/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/pg-listings/**").hasRole("ADMIN")

						// Rooms & tenants: admin-only writes, any logged-in user can read
						.requestMatchers(HttpMethod.POST, "/api/rooms/**", "/api/tenants/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/rooms/**", "/api/tenants/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/rooms/**", "/api/tenants/**").hasRole("ADMIN")

						// Complaints: any authenticated user can file/view, only admin manages/resolves
						.requestMatchers(HttpMethod.POST, "/api/complaints/**").authenticated()
						.requestMatchers(HttpMethod.GET, "/api/complaints/**").authenticated()
						.requestMatchers(HttpMethod.PUT, "/api/complaints/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/complaints/**").hasRole("ADMIN")

						// Payments: admin manages all payment records (creation, marking paid)
						.requestMatchers(HttpMethod.POST, "/api/payments/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/payments/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/api/payments/**").authenticated()

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