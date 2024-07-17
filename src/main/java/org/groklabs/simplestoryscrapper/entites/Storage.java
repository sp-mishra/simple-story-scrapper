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
@Table(name = "storage", schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"connection_name"})
        },
        indexes = {
                @Index(name = "idx_storage_type_name", columnList = "connection_name")
        })
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "connection_name", nullable = false, length = 100)
    private String connectionName;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "storage_type", nullable = false)
    private StorageType storageType;
    private String comments;
    @Column(name = "connection_url")
    private String connectionUrl;
    @Column(name = "connection_user")
    private String connectionUser;
    @Column(name = "connection_password")
    private String connectionPassword;
    @Column(name = "connection_properties", length = 1000)
    private String connectionProperties;
    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;
}
