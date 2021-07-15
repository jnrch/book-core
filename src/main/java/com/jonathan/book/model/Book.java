package com.jonathan.book.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer totalPages;

    @Column(name = "published_date",nullable = false)
    private Date publishedDate;

    @Column(nullable = false)
    @OneToMany(mappedBy = "book", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Publisher> publishers = new ArrayList<>();

    @Column(nullable = false)
    @ManyToMany
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public List<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublisher(List<Publisher> publisher) {
        this.publishers = publishers;
    }

    public void setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }


}
