package org.groklabs.simplestoryscrapper.dtos;

import org.groklabs.simplestoryscrapper.entites.GenreEnum;

import java.util.List;

public record StoryRecord(String title, String author, List<GenreEnum> genres, String url,
                          List<ChapterRecord> chapters) {
}
