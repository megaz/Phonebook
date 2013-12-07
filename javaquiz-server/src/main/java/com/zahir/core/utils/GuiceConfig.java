package com.zahir.core.utils;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.zahir.controller.rest.PhonebookResource;

public class GuiceConfig extends GuiceServletContextListener {

	public static final String JERSEY_API_JSON_POJO_MAPPING_FEATURE = "com.sun.jersey.api.json.POJOMappingFeature";
	public static final String JERSEY_CONFIG_PROPERTY_PACKAGES = "com.sun.jersey.config.property.packages";
	public static final String SERVER_RESOURCES = "com.zahir.controller.rest";

	@Override
	protected Injector getInjector() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put(JERSEY_CONFIG_PROPERTY_PACKAGES, SERVER_RESOURCES);
		params.put(JERSEY_API_JSON_POJO_MAPPING_FEATURE, "true");

		Injector injector = Guice.createInjector(new JerseyServletModule() {
			@Override
			protected void configureServlets() {
				install(new GuiceConfigurationModule());
				bind(PhonebookResource.class);

				serve("/*").with(GuiceContainer.class, params);
			}
		});
		return injector;
	}
}
