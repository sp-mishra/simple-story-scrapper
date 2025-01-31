package org.groklabs.simplestoryscrapper.entites;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "story", schema = "public", indexes = {
        @Index(name = "idx_story_title", columnList = "title")
})
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true)
    private String title;
    private String comment;
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private List<GenreEnum> genre;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    private List<Author> authors;

    @ManyToOne
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    @OrderBy("sequence ASC")
    @ToString.Exclude
    private List<Chapter> chapters;

    @Column(name = "summary_instruction", length = 1000)
    private String summaryInstruction;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}