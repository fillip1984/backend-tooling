package org.home.tooling.backend.controller;

import org.home.tooling.backend.model.dto.Summary;
import org.home.tooling.backend.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/")
@Slf4j
public class HomeController {

    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping
    public String hello() {
        log.info("Welcome home");
        return "index.html";
    }

    // TODO: move to api controller
    @GetMapping("/api/v1/summary")
    @ResponseBody
    public ResponseEntity<Summary> summary() {
        log.info("Retrieving summary");
        return ResponseEntity.ok(homeService.summarize());
    }

}
