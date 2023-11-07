package com.hart.backend.parana.privacy;

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
@Table(name = "privacy")
public class Privacy {

    @Id
    @SequenceGenerator(name = "privacy_sequence", sequenceName = "privacy_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "privacy_sequence")
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "messages")
    private Boolean messages;

    @Column(name = "posts")
    private Boolean posts;

    @Column(name = "comments")
    private Boolean comments;

    @ManyToOne()
    @JoinColumn(name = "blocked_user_id", referencedColumnName = "id")
    private User blockedUser;

    @ManyToOne()
    @JoinColumn(name = "blocked_by_user_id", referencedColumnName = "id")
    private User blockedByUser;

    public Privacy() {

    }

    public Privacy(
            Long id,
            Timestamp createdAt,
            Timestamp updatedAt,
            Boolean messages,
            Boolean posts,
            Boolean comments

    ) {

        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.messages = messages;
        this.posts = posts;
        this.comments = comments;
    }

    public Privacy(
            Boolean messages,
            Boolean posts,
            Boolean comments,
            User blockedUser,
            User blockedByUser) {
        this.messages = messages;
        this.posts = posts;
        this.comments = comments;
        this.blockedUser = blockedUser;
        this.blockedByUser = blockedByUser;
    }

    public Long getId() {
        return id;
    }

    public Boolean getPosts() {
        return posts;
    }

    public Boolean getComments() {
        return comments;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Boolean getMessages() {
        return messages;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public User getBlockedUser() {
        return blockedUser;
    }

    public User getBlockedByUser() {
        return blockedByUser;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPosts(Boolean posts) {
        this.posts = posts;
    }

    public void setComments(Boolean comments) {
        this.comments = comments;
    }

    public void setMessages(Boolean messages) {
        this.messages = messages;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setBlockedUser(User blockedUser) {
        this.blockedUser = blockedUser;
    }

    public void setBlockedByUser(User blockedByUser) {
        this.blockedByUser = blockedByUser;
    }
}
