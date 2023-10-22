package com.hart.backend.parana.connection;

import java.sql.Timestamp;

import com.hart.backend.parana.user.User;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity()
@Table(name = "connection")
public class Connection {

    @Id
    @SequenceGenerator(name = "connection_sequence", sequenceName = "connection_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "connection_sequence")
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "pending")
    private Boolean pending;

    @Column(name = "accepted")
    private Boolean accepted;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private User receiver;

    public Connection() {

    }

    public Connection(
            Long id,
            Timestamp createdAt,
            Timestamp updatedAt,
            Boolean pending,
            Boolean accepted) {

        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.pending = pending;
        this.accepted = accepted;
    }

    public Connection(
            User sender,
            User receiver,
            Boolean pending,
            Boolean accepted) {
        this.sender = sender;
        this.receiver = receiver;
        this.pending = pending;
        this.accepted = accepted;
    }

    public Long getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public User getReceiver() {
        return receiver;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public Boolean getPending() {
        return pending;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
