package org.groklabs.simplestoryscrapper.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "url_jobs", schema = "public",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"url"})
},
        indexes = {
                @Index(name = "idx_url_jobs_url", columnList = "url")
        })
public class UrlJobs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String url;
    @Enumerated(EnumType.STRING)
    private UrlJobsStatus status;
    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;
}
