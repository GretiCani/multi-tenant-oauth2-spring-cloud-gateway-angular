package com.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
@EnableConfigServer
@RestController
public class ConfigServerApplication {
	
	@Autowired
    ResourceLoader resourceLoader;

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

    @GetMapping(value = "{jarName}")
    public InputStreamSource test(@PathVariable(value = "jarName") String jarName) throws MalformedURLException {
        return resourceLoader.getResource(ResourceUtils.extractJarFileURL(new URL("classpath:" + jarName)).toString());
    }

}
