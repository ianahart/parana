package com.hart.backend.parana.story;

import jakarta.persistence.Table;

import java.sql.Timestamp;

import com.hart.backend.parana.user.User;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity()
@Table(name = "story")
public class Story {

    @Id
    @SequenceGenerator(name = "story_sequence", sequenceName = "story_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "story_sequence")
    @Column(name = "id")
    private Long id;

    @CreationTimestamp()
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "expires_in")
    private Long expiresIn;

    @Column(name = "photo_url", length = 400)
    private String photoUrl;

    @Column(name = "photo_filename", length = 400)
    private String photoFileName;

    @Column(name = "text", length = 150)
    private String text;

    @Column(name = "font_size")
    private String fontSize;

    @Column(name = "duration")
    private String duration;

    @Column(name = "color")
    private String color;

    @Column(name = "background")
    private String background;

    @Column(name = "alignment")
    private String alignment;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Story() {

    }

    public Story(
            Long id,
            Timestamp createdAt,
            Long expiresIn,
            String photoUrl,
            String photoFileName,
            String text,
            String fontSize,
            String duration,
            String color,
            String background,
            String alignment) {
        this.id = id;
        this.createdAt = createdAt;
        this.expiresIn = expiresIn;
        this.photoUrl = photoUrl;
        this.photoFileName = photoFileName;
        this.text = text;
        this.fontSize = fontSize;
        this.duration = duration;
        this.color = color;
        this.background = background;
        this.alignment = alignment;
    }

    public Story(
            User user,
            Long expiresIn,
            String photoUrl,
            String photoFileName,
            String text,
            String fontSize,
            String duration,
            String color,
            String background,
            String alignment) {
        this.user = user;
        this.expiresIn = expiresIn;
        this.photoUrl = photoUrl;
        this.photoFileName = photoFileName;
        this.text = text;
        this.fontSize = fontSize;
        this.duration = duration;
        this.color = color;
        this.background = background;
        this.alignment = alignment;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    public String getDuration() {
        return duration;
    }

    public String getFontSize() {
        return fontSize;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public User getUser() {
        return user;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getAlignment() {
        return alignment;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public String getBackground() {
        return background;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

}
