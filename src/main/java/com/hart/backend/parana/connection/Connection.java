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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
        result = prime * result + ((pending == null) ? 0 : pending.hashCode());
        result = prime * result + ((accepted == null) ? 0 : accepted.hashCode());
        result = prime * result + ((sender == null) ? 0 : sender.hashCode());
        result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
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
        Connection other = (Connection) obj;
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
        if (pending == null) {
            if (other.pending != null)
                return false;
        } else if (!pending.equals(other.pending))
            return false;
        if (accepted == null) {
            if (other.accepted != null)
                return false;
        } else if (!accepted.equals(other.accepted))
            return false;
        if (sender == null) {
            if (other.sender != null)
                return false;
        } else if (!sender.equals(other.sender))
            return false;
        if (receiver == null) {
            if (other.receiver != null)
                return false;
        } else if (!receiver.equals(other.receiver))
            return false;
        return true;
    }

}
