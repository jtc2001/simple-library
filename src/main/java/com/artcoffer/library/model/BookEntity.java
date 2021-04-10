package com.artcoffer.library.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="title")
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name="description")
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="pageCount")
    private Integer pageCount;

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    @Column(name="category")
    private String category;

    public void setCategory(String category) {
        this.category = category;
    }

    private BookEntity(){}

    public BookEntity(Long id, String title, String description, Integer pageCount, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pageCount = pageCount;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(pageCount, that.pageCount) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, pageCount, category);
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pageCount=" + pageCount +
                ", category='" + category + '\'' +
                '}';
    }
}
