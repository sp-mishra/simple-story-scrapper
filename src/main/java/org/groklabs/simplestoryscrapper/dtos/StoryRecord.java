package org.groklabs.simplestoryscrapper.dtos;

import org.groklabs.simplestoryscrapper.entites.GenreEnum;

import java.util.List;

public record StoryRecord(String title, List<AuthorRecord> authors, List<GenreEnum> genres, String url,
                          List<ChapterRecord> chapters) {
}
