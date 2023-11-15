package com.hart.backend.parana.setting;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hart.backend.parana.user.User;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity()
@Table(name = "setting")
public class Setting {

    @Id
    @SequenceGenerator(name = "setting_sequence", sequenceName = "setting_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "setting_sequence")
    @Column(name = "id")
    private Long id;

    @CreationTimestamp()
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "remember_me")
    private Boolean rememberMe;

    @Column(name = "block_messages")
    private Boolean blockMessages;

    @Column(name = "block_comments")
    private Boolean blockComments;

    @Column(name = "block_posts")
    private Boolean blockPosts;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "password_updated_on")
    private Timestamp passwordUpdatedOn;

    @Column(name = "email_updated_on")
    private Timestamp emailUpdatedOn;

    @Column(name = "notifications")
    private Boolean notifications;

    @JsonIgnore
    @OneToOne(mappedBy = "setting")
    private User user;

    public Setting() {

    }

    public Setting(
            Long id,
            Timestamp createdAt,
            Boolean rememberMe,
            Boolean blockMessages,
            Boolean blockComments,
            Boolean blockPosts,
            String ipAddress,
            Timestamp passwordUpdatedOn,
            Timestamp emailUpdatedOn,
            Boolean notifications) {
        this.id = id;
        this.createdAt = createdAt;
        this.rememberMe = rememberMe;
        this.blockMessages = blockMessages;
        this.blockComments = blockComments;
        this.blockPosts = blockPosts;
        this.ipAddress = ipAddress;
        this.passwordUpdatedOn = passwordUpdatedOn;
        this.emailUpdatedOn = emailUpdatedOn;
        this.notifications = notifications;
    }

    public Setting(
            Boolean rememberMe,
            Boolean blockMessages,
            Boolean blockComments,
            Boolean blockPosts,
            String ipAddress,
            Boolean notifications) {
        this.rememberMe = rememberMe;
        this.blockMessages = blockMessages;
        this.blockComments = blockComments;
        this.blockPosts = blockPosts;
        this.ipAddress = ipAddress;
        this.notifications = notifications;
    }

    public Long getId() {
        return id;
    }

    public Boolean getNotifications() {
        return notifications;
    }

    public User getUser() {
        return user;
    }

    public Timestamp getEmailUpdatedOn() {
        return emailUpdatedOn;
    }

    public void setEmailUpdatedOn(Timestamp emailUpdatedOn) {
        this.emailUpdatedOn = emailUpdatedOn;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public Boolean getBlockPosts() {
        return blockPosts;
    }

    public Boolean getBlockComments() {
        return blockComments;
    }

    public Boolean getBlockMessages() {
        return blockMessages;
    }

    public Timestamp getPasswordUpdatedOn() {
        return passwordUpdatedOn;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setBlockPosts(Boolean blockPosts) {
        this.blockPosts = blockPosts;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void setBlockComments(Boolean blockComments) {
        this.blockComments = blockComments;
    }

    public void setBlockMessages(Boolean blockMessages) {
        this.blockMessages = blockMessages;
    }

    public void setPasswordUpdatedOn(Timestamp passwordUpdatedOn) {
        this.passwordUpdatedOn = passwordUpdatedOn;
    }

    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((rememberMe == null) ? 0 : rememberMe.hashCode());
        result = prime * result + ((blockMessages == null) ? 0 : blockMessages.hashCode());
        result = prime * result + ((blockComments == null) ? 0 : blockComments.hashCode());
        result = prime * result + ((blockPosts == null) ? 0 : blockPosts.hashCode());
        result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
        result = prime * result + ((passwordUpdatedOn == null) ? 0 : passwordUpdatedOn.hashCode());
        result = prime * result + ((emailUpdatedOn == null) ? 0 : emailUpdatedOn.hashCode());
        result = prime * result + ((notifications == null) ? 0 : notifications.hashCode());
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
        Setting other = (Setting) obj;
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
        if (rememberMe == null) {
            if (other.rememberMe != null)
                return false;
        } else if (!rememberMe.equals(other.rememberMe))
            return false;
        if (blockMessages == null) {
            if (other.blockMessages != null)
                return false;
        } else if (!blockMessages.equals(other.blockMessages))
            return false;
        if (blockComments == null) {
            if (other.blockComments != null)
                return false;
        } else if (!blockComments.equals(other.blockComments))
            return false;
        if (blockPosts == null) {
            if (other.blockPosts != null)
                return false;
        } else if (!blockPosts.equals(other.blockPosts))
            return false;
        if (ipAddress == null) {
            if (other.ipAddress != null)
                return false;
        } else if (!ipAddress.equals(other.ipAddress))
            return false;
        if (passwordUpdatedOn == null) {
            if (other.passwordUpdatedOn != null)
                return false;
        } else if (!passwordUpdatedOn.equals(other.passwordUpdatedOn))
            return false;
        if (emailUpdatedOn == null) {
            if (other.emailUpdatedOn != null)
                return false;
        } else if (!emailUpdatedOn.equals(other.emailUpdatedOn))
            return false;
        if (notifications == null) {
            if (other.notifications != null)
                return false;
        } else if (!notifications.equals(other.notifications))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

}
