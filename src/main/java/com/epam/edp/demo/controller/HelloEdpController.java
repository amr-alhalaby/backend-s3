package com.epam.edp.demo.controller;

import com.epam.edp.demo.service.S3DataService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pavlo_Yemelianov
 */
@RestController
public class HelloEdpController {
    private static final Logger log = LoggerFactory.getLogger(HelloEdpController.class);

    private final S3DataService s3DataService;

    public HelloEdpController(S3DataService s3DataService) {
        this.s3DataService = s3DataService;
    }

    @GetMapping(value = "/")
    public Map<String, String> root() {
        log.info("Handling request to /");
        String content = s3DataService.getDataContent();
        log.info("Returning S3 content for /");
        return Map.of("content", content);
    }

    @GetMapping(value = "/api/hello")
    public String hello() {
        return "Hello, EDP!";
    }
}
