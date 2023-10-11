package com.hart.backend.parana.profile;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hart.backend.parana.user.User;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @SequenceGenerator(name = "profile_sequence", sequenceName = "profile_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_sequence")
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_At")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "avatar_filename", length = 300)
    private String avatarFilename;

    @Column(name = "avatar_url", length = 400)
    private String avatarUrl;

    @Column(name = "bio", length = 400)
    private String bio;

    @Column(name = "specialities", length = 400)
    private String specialities;

    @Column(name = "location", length = 300)
    private String location;

    @JsonIgnore
    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private User user;

    public Profile() {

    }

    public Profile(
            Long id,
            Timestamp createdAt,
            Timestamp updatedAt,
            String avatarFilename,
            String avatarUrl,
            String bio,
            String specialities,
            String location) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.avatarFilename = avatarFilename;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.specialities = specialities;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }

    public User getUser() {
        return user;
    }

    public String getLocation() {
        return location;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public String getSpecialities() {
        return specialities;
    }

    public String getAvatarFilename() {
        return avatarFilename;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public void setAvatarFilename(String avatarFilename) {
        this.avatarFilename = avatarFilename;
    }

}
