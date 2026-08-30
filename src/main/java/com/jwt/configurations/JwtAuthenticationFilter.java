package com.jwt.configurations;
import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.model.Register;
import com.jwt.repository.RegisterRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenGenerator jwtTokenGenerator;

	private final RegisterRepository registerRepository;

	public JwtAuthenticationFilter(JwtTokenGenerator jwtTokenGenerator, RegisterRepository registerRepository) {

		super();
		this.jwtTokenGenerator = jwtTokenGenerator;
		this.registerRepository = registerRepository;

	}

	private String extractTokenFromHeader(HttpServletRequest request) {

		String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {

			return null;

		} else {

			return header.substring(7);

		}

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = extractTokenFromHeader(request);

		if (token != null) {

			try {

				String userId = jwtTokenGenerator.extractUserId(token);

				if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

					Register register = registerRepository.findById(Long.valueOf(userId))
							.orElseThrow(() -> new RuntimeException("User Not Found"));
					List<GrantedAuthority> authorities = register.getRoles().stream()
							.map(roles -> (GrantedAuthority) new SimpleGrantedAuthority(roles.name())).toList();
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId,
							null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authentication);

				}

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

		filterChain.doFilter(request, response);

	}

}
