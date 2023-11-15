package com.hart.backend.parana.comment;

import java.sql.Timestamp;
import java.util.List;

import com.hart.backend.parana.commentlike.CommentLike;
import com.hart.backend.parana.post.Post;
import com.hart.backend.parana.replycomment.ReplyComment;
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
@Table(name = "comment")
public class Comment {

    @Id
    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "text", length = 300)
    private String text;

    @Column(name = "is_edited")
    private Boolean isEdited;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> commentCommentLikes;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReplyComment> commentReplyComments;

    public Comment() {

    }

    public Comment(
            Long id,
            Timestamp createdAt,
            Timestamp updatedAt,
            String text,
            Boolean isEdited) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.text = text;
        this.isEdited = isEdited;
    }

    public Comment(
            String text,
            Boolean isEdited,
            User user,
            Post post) {
        this.text = text;
        this.isEdited = isEdited;
        this.user = user;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public List<ReplyComment> getCommentReplyComments() {
        return commentReplyComments;
    }

    public List<CommentLike> getCommentCommentLikes() {
        return commentCommentLikes;
    }

    public Post getPost() {
        return post;
    }

    public String getText() {
        return text;
    }

    public Boolean getIsEdited() {
        return isEdited;
    }

    public User getUser() {
        return user;
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

    public void setCommentCommentLikes(List<CommentLike> commentCommentLikes) {
        this.commentCommentLikes = commentCommentLikes;
    }

    public void setCommentReplyComments(List<ReplyComment> commentReplyComments) {
        this.commentReplyComments = commentReplyComments;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIsEdited(Boolean isEdited) {
        this.isEdited = isEdited;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        result = prime * result + ((isEdited == null) ? 0 : isEdited.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((post == null) ? 0 : post.hashCode());
        result = prime * result + ((commentCommentLikes == null) ? 0 : commentCommentLikes.hashCode());
        result = prime * result + ((commentReplyComments == null) ? 0 : commentReplyComments.hashCode());
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
        Comment other = (Comment) obj;
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
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        if (isEdited == null) {
            if (other.isEdited != null)
                return false;
        } else if (!isEdited.equals(other.isEdited))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        if (post == null) {
            if (other.post != null)
                return false;
        } else if (!post.equals(other.post))
            return false;
        if (commentCommentLikes == null) {
            if (other.commentCommentLikes != null)
                return false;
        } else if (!commentCommentLikes.equals(other.commentCommentLikes))
            return false;
        if (commentReplyComments == null) {
            if (other.commentReplyComments != null)
                return false;
        } else if (!commentReplyComments.equals(other.commentReplyComments))
            return false;
        return true;
    }

}
