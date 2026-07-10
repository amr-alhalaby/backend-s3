package com.epam.edp.demo.controller;

import com.epam.edp.demo.service.S3DataService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pavlo_Yemelianov
 */
@RestController
public class HelloEdpController {
    private final S3DataService s3DataService;

    public HelloEdpController(S3DataService s3DataService) {
        this.s3DataService = s3DataService;
    }

    @GetMapping(value = "/")
    public Map<String, String> root() {
        return Map.of("content", s3DataService.getDataContent());
    }

    @GetMapping(value = "/api/hello")
    public String hello() {
        return "Hello, EDP!";
    }
}
