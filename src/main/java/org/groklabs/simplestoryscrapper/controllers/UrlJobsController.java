package org.groklabs.simplestoryscrapper.controllers;

import org.groklabs.simplestoryscrapper.dtos.UrlJobsDTO;
import org.groklabs.simplestoryscrapper.services.UrlJobsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class UrlJobsController {

    private final UrlJobsService urlJobsService;

    public UrlJobsController(UrlJobsService urlJobsService) {
        this.urlJobsService = urlJobsService;
    }

    @PostMapping("/addUrl")
    public ResponseEntity<UrlJobsDTO> addUrlJob(@RequestBody String url) {
        final UrlJobsDTO savedDto = urlJobsService.saveUrlJob(url);
        return ResponseEntity.ok(savedDto);
    }
}