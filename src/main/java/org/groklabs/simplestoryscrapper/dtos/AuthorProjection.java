package org.groklabs.simplestoryscrapper.dtos;

public interface AuthorProjection {
    String getFirstName();
    String getMiddleName();
    String getLastName();
    String getPenName();
    String getComments();

    default String getFullName() {
        String middle = getMiddleName() == null ? "" : " " + getMiddleName();
        return getFirstName() + middle + " " + getLastName();
    }
}