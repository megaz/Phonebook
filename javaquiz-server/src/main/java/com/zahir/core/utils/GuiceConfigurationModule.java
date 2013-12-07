package com.zahir.core.utils;

import com.google.inject.AbstractModule;
import com.zahir.service.PhonebookService;
import com.zahir.service.PhonebookServiceImpl;

public class GuiceConfigurationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PhonebookService.class).to(PhonebookServiceImpl.class);
	}
}