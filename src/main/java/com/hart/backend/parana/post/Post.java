package com.hart.backend.parana.post;

import java.sql.Timestamp;
import java.util.List;

import com.hart.backend.parana.postlike.PostLike;
import com.hart.backend.parana.user.User;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity()
@Table(name = "post")
public class Post {

    @Id
    @SequenceGenerator(name = "post_sequence", sequenceName = "post_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_sequence")
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_edited")
    private Boolean isEdited;

    @Column(name = "text", length = 600)
    private String text;

    @Column(name = "gif", length = 600)
    private String gif;

    @Column(name = "filename", length = 400)
    private String filename;

    @Column(name = "file_url", length = 600)
    private String fileUrl;

    @ManyToOne()
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @ManyToOne()
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postPostLikes;

    public Post() {

    }

    public Post(
            Long id,
            Timestamp createdAt,
            Timestamp updatedAt,
            Boolean isEdited,
            String text,
            String gif,
            String filename,
            String fileUrl) {

        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isEdited = isEdited;
        this.text = text;
        this.gif = gif;
        this.filename = filename;
        this.fileUrl = fileUrl;
    }

    public Post(
            Boolean isEdited,
            String text,
            String gif,
            String filename,
            String fileUrl,
            User owner,
            User author) {

        this.isEdited = isEdited;
        this.text = text;
        this.gif = gif;
        this.filename = filename;
        this.fileUrl = fileUrl;
        this.owner = owner;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public List<PostLike> getPostPostLikes() {
        return postPostLikes;
    }

    public String getGif() {
        return gif;
    }

    public String getText() {
        return text;
    }

    public User getOwner() {
        return owner;
    }

    public User getAuthor() {
        return author;
    }

    public String getFilename() {
        return filename;
    }

    public Boolean getIsEdited() {
        return isEdited;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setIsEdited(Boolean isEdited) {
        this.isEdited = isEdited;
    }

    public void setPostPostLikes(List<PostLike> postPostLikes) {
        this.postPostLikes = postPostLikes;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
