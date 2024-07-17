package org.groklabs.simplestoryscrapper.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "author", schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"first_name", "middle_name", "last_name", "pen_name"})
        },
        indexes = {
                @Index(name = "idx_author_names", columnList = "first_name, last_name, pen_name")
        })
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;
    @Column(name = "middle_name", length = 50)
    private String middleName;
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
    @Column(name = "pen_name", nullable = false, length = 100)
    private String penName;
    @Column(name = "active_from")
    private LocalDate activeFrom;
    @Column(name = "active_to")
    private LocalDate activeTo;
    private String comments;
}