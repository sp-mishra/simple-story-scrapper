package org.groklabs.simplestoryscrapper.repos;

import org.groklabs.simplestoryscrapper.entites.Author;
import org.groklabs.simplestoryscrapper.dtos.AuthorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<AuthorProjection> findBy();
}