package com.service2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/index2")
@SpringBootApplication
@EnableDiscoveryClient
public class Service2Application {
	
	@Autowired
	private WebClient webClient;

	public static void main(String[] args) {
        SpringApplication.run(Service2Application.class, args);
    }
	
	@GetMapping
	public Mono<String> index1(){
		return Mono.just("Hello From Service 2");
	}

	
}