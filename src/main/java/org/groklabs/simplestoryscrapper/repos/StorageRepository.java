package org.groklabs.simplestoryscrapper.repos;

import org.groklabs.simplestoryscrapper.entites.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
    // Custom query methods can be added here
}