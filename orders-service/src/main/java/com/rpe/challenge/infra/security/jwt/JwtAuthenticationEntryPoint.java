package com.rpe.challenge.infra.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpe.challenge.shared.exceptions.ForbiddenException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final ObjectMapper objectMapper;

	@Override
	public void commence(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {

		ForbiddenException ex = new ForbiddenException(
			"IN.AUTH-REQ.403",
			!hasToken(request)
				? "Acesso não autorizado"
				: "Você não possui acesso à este recurso"
		);

		response.setStatus(ex.getStatus().value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(ex.toResponse()));
	}

	private boolean hasToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		return token != null && token.startsWith("Bearer ");
	}
}