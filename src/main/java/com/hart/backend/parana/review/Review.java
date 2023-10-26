package com.hart.backend.parana.review;

import java.sql.Timestamp;

import com.hart.backend.parana.user.User;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity()
@Table(name = "review")
public class Review {

    @Id
    @SequenceGenerator(name = "review_sequence", sequenceName = "review_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "rating")
    private Byte rating;

    @Column(name = "review", length = 400)
    private String review;

    @Column(name = "is_edited")
    private Boolean isEdited;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private User teacher;

    public Review() {

    }

    public Review(
            Long id,
            Timestamp createdAt,
            Timestamp updatedAt,
            Byte rating,
            String review,
            Boolean isEdited) {

        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.rating = rating;
        this.review = review;
        this.isEdited = isEdited;
    }

    public Review(Byte rating, String review, User user, User teacher, Boolean isEdited) {
        this.rating = rating;
        this.review = review;
        this.user = user;
        this.teacher = teacher;
        this.isEdited = isEdited;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Byte getRating() {
        return rating;
    }

    public Boolean getIsEdited() {
        return isEdited;
    }

    public String getReview() {
        return review;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
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

    public void setIsEdited(Boolean isEdited) {
        this.isEdited = isEdited;
    }
}
