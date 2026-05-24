package com.rpe.challenge.infra.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpe.challenge.auth.security.UserPrincipal;
import com.rpe.challenge.infra.security.jwt.JwtService;
import com.rpe.challenge.shared.exceptions.BaseException;
import com.rpe.challenge.shared.exceptions.UnauthorizedException;
import com.rpe.challenge.shared.exceptions.core.ExceptionContext;
import com.rpe.challenge.shared.exceptions.core.ExceptionModuleType;
import com.rpe.challenge.users.persistence.entities.UserEntity;
import com.rpe.challenge.users.persistence.repositories.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	private final ObjectMapper objectMapper;
	private final UserRepository userRepository;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {

		String token = recoverToken(request);
		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}
		try {
			if (ExceptionContext.get() == null) {
				ExceptionContext.set(ExceptionModuleType.AUTH);
			}

			String subject = jwtService.extractSubject(token);
			Optional<UserEntity> userEntity = userRepository.findByEmail(subject);

			if (userEntity.isEmpty() || !jwtService.isTokenValid(token, subject)) {
				filterChain.doFilter(request, response);
				return;
			}

			UserDetails userPrincipal = new UserPrincipal(userEntity.get());
			var authentication = new UsernamePasswordAuthenticationToken(
				userPrincipal,
				null,
				userPrincipal.getAuthorities()
			);

			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (UsernameNotFoundException ex) {
			throwException(response, new UnauthorizedException(
				"IN.AUTH-NFD.401",
				"Acesso não autenticado"
			));

			return;
		} catch (ExpiredJwtException ex) {
			throwException(response, new UnauthorizedException(
				"IN.AUTH-EXP.401",
				"Sessão expirada"
			));

			return;
		} catch (MalformedJwtException ex) {
			throwException(response, new UnauthorizedException(
				"IN.AUTH-MFD.401",
				"Sessão inválida"
			));

			return;
		} catch (Exception ex) {
			throwException(response, new UnauthorizedException(
				"IN.AUTH-GNC.401",
				"Sessão inválida"
			));

			return;
		} finally {
			ExceptionContext.clear();
		}

		filterChain.doFilter(request, response);
	}

	private String recoverToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			return header.substring(7);
		}

		return null;
	}

	private void throwException(HttpServletResponse response, BaseException ex) throws IOException {
		response.setStatus(ex.getStatus().value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(ex.toResponse()));
	}
}
