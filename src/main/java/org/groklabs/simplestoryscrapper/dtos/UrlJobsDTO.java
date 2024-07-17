package org.groklabs.simplestoryscrapper.dtos;

import org.groklabs.simplestoryscrapper.entites.UrlJobsStatus;

public record UrlJobsDTO(String url, UrlJobsStatus status) {}