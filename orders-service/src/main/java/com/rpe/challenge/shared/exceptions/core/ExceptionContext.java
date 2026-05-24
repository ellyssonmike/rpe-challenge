package com.rpe.challenge.shared.exceptions.core;

public class ExceptionContext {

	private static final ThreadLocal<ExceptionModuleType> CONTEXT = new ThreadLocal<>();

	public static void set(ExceptionModuleType module) {
		CONTEXT.set(module);
	}

	public static ExceptionModuleType get() {
		return CONTEXT.get();
	}

	public static void clear() {
		CONTEXT.remove();
	}
}