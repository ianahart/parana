package com.hart.backend.parana.recommendation;

import java.sql.Timestamp;

import com.hart.backend.parana.user.User;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity()
@Table(name = "recommendation")
public class Recommendation {

    @Id
    @SequenceGenerator(name = "recommendation_sequence", sequenceName = "recommendation_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recommendation_sequence")
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "words")
    private String words;

    @Column(name = "recommendation", length = 400)
    private String recommendation;

    @ManyToOne()
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private User teacher;

    @ManyToOne()
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    public Recommendation() {

    }

    public Recommendation(
            Long id,
            Timestamp createdAt,
            Timestamp updatedAt,
            String words,
            String recommendation) {

        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.words = words;
        this.recommendation = recommendation;
    }

    public Recommendation(
            String recommendation,
            String words,
            User teacher,
            User author) {

        this.recommendation = recommendation;
        this.words = words;
        this.teacher = teacher;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getWords() {
        return words;
    }

    public User getAuthor() {
        return author;
    }

    public User getTeacher() {
        return teacher;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
