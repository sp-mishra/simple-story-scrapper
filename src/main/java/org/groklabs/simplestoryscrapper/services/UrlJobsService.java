package org.groklabs.simplestoryscrapper.services;

import jakarta.annotation.Nonnull;
import org.groklabs.simplestoryscrapper.dtos.UrlJobsDTO;
import org.groklabs.simplestoryscrapper.entites.UrlJobs;
import org.groklabs.simplestoryscrapper.entites.UrlJobsStatus;
import org.groklabs.simplestoryscrapper.exceptions.DuplicateUrlException;
import org.groklabs.simplestoryscrapper.repos.UrlJobsRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UrlJobsService {

    private final UrlJobsRepository urlJobsRepository;

    public UrlJobsService(UrlJobsRepository urlJobsRepository) {
        this.urlJobsRepository = urlJobsRepository;
    }

    public UrlJobsDTO saveUrlJob(@Nonnull String url) {
        UrlJobsDTO dto = new UrlJobsDTO(url, UrlJobsStatus.PENDING);
        try {
            // Assuming conversion from DTO to entity is handled here
            final UrlJobs entity = convertToEntity(dto);
            urlJobsRepository.save(entity);
            return dto; // Or convert entity back to DTO if needed
        } catch (DataIntegrityViolationException e) {
            final Throwable rootCause = e.getMostSpecificCause();
            if (rootCause instanceof ConstraintViolationException) {
                throw new DuplicateUrlException("Duplicate URL: " + dto.url());
            }
            throw e; // Rethrow if it's not a ConstraintViolationException
        }
    }

    private UrlJobs convertToEntity(@Nonnull UrlJobsDTO dto) {
        final UrlJobs entity = new UrlJobs();
        entity.setUrl(dto.url());
        entity.setStatus(dto.status());
        return entity;
    }
}