package org.groklabs.simplestoryscrapper.repos;

import org.groklabs.simplestoryscrapper.entites.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    // Custom query methods can be added here
}