package com.hart.backend.parana.recentsearch;

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
@Table(name = "recent_search")
public class RecentSearch {

    @Id
    @SequenceGenerator(name = "recent_search_sequence", sequenceName = "recent_search_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recent_search_sequence")
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "term", length = 200)
    private String term;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public RecentSearch() {

    }

    public RecentSearch(
            Long id,
            Timestamp createdAt,
            Timestamp updatedAt,
            String term) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.term = term;
    }

    public RecentSearch(User user, String term) {
        this.user = user;
        this.term = term;
    }

    public Long getId() {
        return id;
    }

    public String getTerm() {
        return term;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
