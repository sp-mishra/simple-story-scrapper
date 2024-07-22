package org.groklabs.simplestoryscrapper.dtos;

public record AuthorRecord(String firstName, String middleName, String lastName, String penName,
                           String activeFrom, String activeTo, String comments) {
}
