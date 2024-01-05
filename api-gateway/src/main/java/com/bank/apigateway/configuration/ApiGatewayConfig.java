package com.bank.apigateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ApiGatewayConfig {
	@Value("${service.customer.baseUrl}")
	private String customerBaseUrl;

	@Value("${service.customer.name}")
	private String customerServiceName;

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder locatorBuilder) {
		Function<PredicateSpec, Buildable<Route>> routeFunction
				= p -> p.path(customerBaseUrl)
				.uri("lb://" + customerServiceName);
		return locatorBuilder
				.routes()
				.route(routeFunction)
				.build();
	}
}
