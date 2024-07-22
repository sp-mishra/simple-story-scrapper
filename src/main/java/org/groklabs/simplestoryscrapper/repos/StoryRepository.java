package org.groklabs.simplestoryscrapper.repos;

import org.groklabs.simplestoryscrapper.entites.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    int deleteByCreatedAtBefore(LocalDateTime dateTime);
}