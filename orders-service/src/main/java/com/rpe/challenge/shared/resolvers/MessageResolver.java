package com.rpe.challenge.shared.resolvers;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageResolver {

	private static MessageSource messageSource;

	public MessageResolver(MessageSource source) {
		MessageResolver.messageSource = source;
	}

	public static String get(String key) {
		return messageSource.getMessage(
			key,
			null,
			LocaleContextHolder.getLocale()
		);
	}
}