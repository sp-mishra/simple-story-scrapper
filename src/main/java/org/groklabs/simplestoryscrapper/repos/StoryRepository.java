package org.groklabs.simplestoryscrapper.repos;

import org.groklabs.simplestoryscrapper.entites.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    // Custom query methods can be added here
}