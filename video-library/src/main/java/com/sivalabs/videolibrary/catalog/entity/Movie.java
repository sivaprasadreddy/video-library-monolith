package com.sivalabs.videolibrary.catalog.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movies")
@Setter
@Getter
@EqualsAndHashCode(
        of = {"id"},
        callSuper = false)
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            name = "movie_id_generator",
            sequenceName = "movie_id_seq",
            allocationSize = 1)
    @GeneratedValue(generator = "movie_id_generator")
    private Long id;

    private String title;

    @JsonProperty("tmdb_id")
    @Column(name = "tmdb_id")
    private Long tmdbId;

    @JsonProperty("imdb_id")
    @Column(name = "imdb_id")
    private String imdbId;

    private String overview;

    private String tagline;

    private Integer runtime;

    private Double revenue;

    @JsonProperty("release_date")
    @Column(name = "release_date")
    private LocalDate releaseDate;

    @JsonProperty("poster_path")
    @Column(name = "poster_path")
    private String posterPath;

    private Double budget;

    private String homepage;

    @JsonProperty("original_language")
    @Column(name = "original_language")
    private String originalLanguage;

    @JsonProperty("vote_average")
    @Column(name = "vote_average")
    private Double voteAverage;

    @JsonProperty("vote_count")
    @Column(name = "vote_count")
    private Long voteCount;

    @Column(nullable = false)
    private BigDecimal price;

    @JsonProperty("created_at")
    @Column(updatable = false)
    protected LocalDateTime createdAt = LocalDateTime.now();

    @JsonProperty("updated_at")
    @Column(insertable = false)
    protected LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "movie_genre",
            joinColumns = {@JoinColumn(name = "MOVIE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "GENRE_ID", referencedColumnName = "ID")})
    private Set<Genre> genres = new HashSet<>();

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
