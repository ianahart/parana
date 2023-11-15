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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
        result = prime * result + ((messages == null) ? 0 : messages.hashCode());
        result = prime * result + ((posts == null) ? 0 : posts.hashCode());
        result = prime * result + ((comments == null) ? 0 : comments.hashCode());
        result = prime * result + ((blockedUser == null) ? 0 : blockedUser.hashCode());
        result = prime * result + ((blockedByUser == null) ? 0 : blockedByUser.hashCode());
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
        Privacy other = (Privacy) obj;
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
        if (updatedAt == null) {
            if (other.updatedAt != null)
                return false;
        } else if (!updatedAt.equals(other.updatedAt))
            return false;
        if (messages == null) {
            if (other.messages != null)
                return false;
        } else if (!messages.equals(other.messages))
            return false;
        if (posts == null) {
            if (other.posts != null)
                return false;
        } else if (!posts.equals(other.posts))
            return false;
        if (comments == null) {
            if (other.comments != null)
                return false;
        } else if (!comments.equals(other.comments))
            return false;
        if (blockedUser == null) {
            if (other.blockedUser != null)
                return false;
        } else if (!blockedUser.equals(other.blockedUser))
            return false;
        if (blockedByUser == null) {
            if (other.blockedByUser != null)
                return false;
        } else if (!blockedByUser.equals(other.blockedByUser))
            return false;
        return true;
    }

}
