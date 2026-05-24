package com.rpe.challenge.shared.exceptions.core;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ExceptionModuleInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	) {

		if (handler instanceof HandlerMethod handlerMethod) {
			ExceptionModule annotation = handlerMethod.getBeanType()
				.getAnnotation(ExceptionModule.class);

			if (annotation != null) {
				ExceptionContext.set(annotation.value());
			}
		}

		return true;
	}

	@Override
	public void afterCompletion(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler,
		Exception ex
	) {
		ExceptionContext.clear();
	}
}