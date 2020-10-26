package com.service1;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

public class TokenResolver {

    public static String resolve(ServerWebExchange exchange){
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        return headers.get("Authorization").get(0);
    }
}
