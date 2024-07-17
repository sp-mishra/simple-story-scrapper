package org.groklabs.simplestoryscrapper.repos;

import org.groklabs.simplestoryscrapper.entites.UrlJobs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlJobsRepository extends JpaRepository<UrlJobs, Long> {
    // Custom query methods can be added here
}
