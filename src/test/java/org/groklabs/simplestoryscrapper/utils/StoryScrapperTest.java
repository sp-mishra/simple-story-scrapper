package org.groklabs.simplestoryscrapper.utils;

import org.groklabs.simplestoryscrapper.entites.GenreEnum;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

class StoryScrapperTest {
    @Test
    void when_RunIsCalled_Expect_ValidDocument() {
        // Given
        String url = "https://www.gutenberg.org/cache/epub/11027/pg11027-images.html";
        final List<GenreEnum> genreEnums = new ArrayList<>();
        genreEnums.add(GenreEnum.FANTASY);
        final StoryScrapper storyScrapper = new StoryScrapper(url, genreEnums);

        // When
        storyScrapper.run();

        // Then
        assertNotNull(storyScrapper.document);
    }
}