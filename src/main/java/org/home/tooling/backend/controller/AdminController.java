package org.home.tooling.backend.controller;

import java.time.LocalDateTime;

import org.home.tooling.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/load-sample-data")
    public ResponseEntity<String> loadSampleData() {

        log.warn("Not yet implemented");

        var msg = "Loading sample data";
        log.info(msg);
        // adminService.loadSampleData();

        return ResponseEntity.ok("success");
    }

    @PostMapping("/import")
    public ResponseEntity<String> importData(MultipartFile file) {

        log.warn("Not yet implemented");

        try {
            log.info("Performing import");
            // var data = objectMapper.readValue(file.getInputStream(), DataExport.class);

            // adminService.importData(data);

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            var msg = "Exception occurred while attempting to import data";
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> exportData() {

        log.warn("Not yet implemented");

        try {
            log.info("Performing export");
            // DataExport data = adminService.exportData();
            StringBuilder out = new StringBuilder();

            // out.append(objectMapper.writeValueAsString(data));

            log.trace("Json export result: {}", out.toString());

            ByteArrayResource resource = new ByteArrayResource(out.toString().getBytes());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=export_" + LocalDateTime.now() + ".json");
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            log.info("Performed export");

            // @formatter:off
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
            // @formatter:on
        } catch (Exception e) {

            var msg = "Exception occurred while exporting data";
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }
}
