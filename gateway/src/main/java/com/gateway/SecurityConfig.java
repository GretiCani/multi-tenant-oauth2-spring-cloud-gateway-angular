package com.gateway;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerReactiveAuthenticationManagerResolver;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

import java.util.List;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

	@Bean
	JwtIssuerReactiveAuthenticationManagerResolver resolver(){
		return new JwtIssuerReactiveAuthenticationManagerResolver("http://172.17.0.1:8080/auth/realms/tenant_2:8080/auth/realms/tenant_1","http://172.17.0.1:8080/auth/realms/tenant_2");
	}



	public static final String API_MATCHER_PATH = "/**";
	private final ApplicationContext context;

	public SecurityConfig(final ApplicationContext context) {
		this.context = context;
	}

	@Bean
	WebClient tokenAugmentingWebClient(final ReactiveClientRegistrationRepository clientRegistrationRepository,
									   final ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {
		return WebClient.builder()
			.filter(new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, authorizedClientRepository))
			.build();
	}

	@Bean
	public SecurityWebFilterChain securityWebFilterChain() {
		// the matcher for all paths that need to be secured (require a logged-in user)
		final ServerWebExchangeMatcher apiPathMatcher = pathMatchers(API_MATCHER_PATH);

		// default chain for all requests
		final ServerHttpSecurity http = this.context.getBean(ServerHttpSecurity.class);

		 http.cors().and().csrf()
		        .disable()
			.authorizeExchange()
			.matchers(PathRequest.toStaticResources().atCommonLocations())
	        .permitAll()
	        .matchers(EndpointRequest.to("health"))
	        .permitAll()
	        .matchers(EndpointRequest.to("info"))
	        .permitAll()
			.pathMatchers("/*")
			.permitAll()
			.anyExchange().authenticated()
			.and()
            .oauth2Client()
            .and()
		.oauth2ResourceServer(oauth2-> oauth2.authenticationManagerResolver(resolver()));

		 return http.build();
	}	
}