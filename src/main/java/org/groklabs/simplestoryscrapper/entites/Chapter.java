package org.groklabs.simplestoryscrapper.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "chapter", schema = "public")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "sequence", nullable = false, unique = true)
    private Long sequence;

    @Column(name = "parent_location")
    private String parentLocation;

    @OneToMany(mappedBy = "chapter")
    @Column(name = "unrefined_content")
    @ToString.Exclude
    @OrderBy("order ASC")
    private List<Chunk> unrefinedContent;

    @OneToMany(mappedBy = "chapter")
    @Column(name = "refined_content")
    @OrderBy("order ASC")
    @ToString.Exclude
    private List<Chunk> refinedContent;

    @OneToMany(mappedBy = "chapter")
    @Column(name = "chapter_summary")
    @OrderBy("order ASC")
    @ToString.Exclude
    private List<Chunk> summaryChunks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id")
    @ToString.Exclude
    private Story story;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_chapter_id")
    @ToString.Exclude
    private Chapter parentChapter;

    @OneToMany(mappedBy = "parentChapter")
    @ToString.Exclude
    private List<Chapter> subChapters;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;
}