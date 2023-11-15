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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((expiresIn == null) ? 0 : expiresIn.hashCode());
        result = prime * result + ((photoUrl == null) ? 0 : photoUrl.hashCode());
        result = prime * result + ((photoFileName == null) ? 0 : photoFileName.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        result = prime * result + ((fontSize == null) ? 0 : fontSize.hashCode());
        result = prime * result + ((duration == null) ? 0 : duration.hashCode());
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((background == null) ? 0 : background.hashCode());
        result = prime * result + ((alignment == null) ? 0 : alignment.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Story other = (Story) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!createdAt.equals(other.createdAt))
            return false;
        if (expiresIn == null) {
            if (other.expiresIn != null)
                return false;
        } else if (!expiresIn.equals(other.expiresIn))
            return false;
        if (photoUrl == null) {
            if (other.photoUrl != null)
                return false;
        } else if (!photoUrl.equals(other.photoUrl))
            return false;
        if (photoFileName == null) {
            if (other.photoFileName != null)
                return false;
        } else if (!photoFileName.equals(other.photoFileName))
            return false;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        if (fontSize == null) {
            if (other.fontSize != null)
                return false;
        } else if (!fontSize.equals(other.fontSize))
            return false;
        if (duration == null) {
            if (other.duration != null)
                return false;
        } else if (!duration.equals(other.duration))
            return false;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
            return false;
        if (background == null) {
            if (other.background != null)
                return false;
        } else if (!background.equals(other.background))
            return false;
        if (alignment == null) {
            if (other.alignment != null)
                return false;
        } else if (!alignment.equals(other.alignment))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

}
