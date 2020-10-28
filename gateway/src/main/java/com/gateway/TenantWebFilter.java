package com.gateway;


import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
;
import java.text.ParseException;

import java.util.List;


@Component
public class TenantWebFilter implements WebFilter{

   private  String tenant="";

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain){
        if (serverWebExchange.getRequest().getHeaders().get("Authorization") != null) {
            try{ tenant = this.getTenant(serverWebExchange.getRequest().getHeaders().get("Authorization"));}
            catch (ParseException e) { e.printStackTrace(); }
            return tenant.isBlank()?webFilterChain.filter(serverWebExchange): webFilterChain.filter(serverWebExchange).subscriberContext(context -> context.put("tenant", tenant));
        }
        return webFilterChain.filter(serverWebExchange);
    }

    public String getTenant(List<String> headers) throws ParseException {

        String token = headers.get(0).split(" ")[1];
        JWT jwt = JWTParser.parse(token);
        return (String) jwt.getJWTClaimsSet().getClaims().get("tenant_id");


    }

}
