package org.groklabs.simplestoryscrapper.utils;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.groklabs.simplestoryscrapper.dtos.StoryRecord;
import org.groklabs.simplestoryscrapper.entites.GenreEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class StoryScrapper {
    final String url;
    final List<GenreEnum> genreEnum;
    Document document;
    StoryRecord storyRecord;

    public StoryScrapper(@Nonnull String url, @Nonnull List<GenreEnum> genreEnum) {
        this.url = url;
        this.genreEnum = genreEnum;
    }

    public void run() {
        try {
            document = Jsoup.connect(url).get();
            final String title = document.title();
            // Expanded CSS query to include paragraphs, divs, and headings
            String cssQuery = "h2, div";
            final Set<String> text = new HashSet<>();
            document.select(cssQuery).forEach(element -> {
                if (cssQuery.equalsIgnoreCase("h2")) {
                    text.add(element.text() + "====================");
                } else {
                    text.add(element.text());
                }
            });
            text.forEach(log::info);
        } catch (IOException e) {
            log.error("Error fetching or parsing document: {}", e.getMessage());
        }
    }
}
