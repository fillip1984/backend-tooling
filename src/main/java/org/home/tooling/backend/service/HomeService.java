package org.home.tooling.backend.service;

import org.home.tooling.backend.model.dto.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HomeService {

    @Autowired
    private TodoService todoService;

    public Summary summarize() {
        log.info("Building summary");
        var summary = Summary.builder()
                .todoCount(todoService.count())
                .build();
        return summary;
    }

}
