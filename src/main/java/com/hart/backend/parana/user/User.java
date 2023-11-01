package com.hart.backend.parana.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.sql.Timestamp;
import java.util.List;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.hart.backend.parana.comment.Comment;
import com.hart.backend.parana.connection.Connection;
import com.hart.backend.parana.favorite.Favorite;
import com.hart.backend.parana.passwordreset.PasswordReset;
import com.hart.backend.parana.post.Post;
import com.hart.backend.parana.postlike.PostLike;
import com.hart.backend.parana.profile.Profile;
import com.hart.backend.parana.recentsearch.RecentSearch;
import com.hart.backend.parana.recommendation.Recommendation;
import com.hart.backend.parana.review.Review;
import com.hart.backend.parana.token.Token;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity()
@Table(name = "_user")

public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "_user_sequence", sequenceName = "_user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "_user_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", length = 200)
    private String firstName;

    @Column(name = "last_name", length = 200)
    private String lastName;

    @Column(name = "full_name", length = 400)
    private String fullName;

    @Column(name = "email", unique = true)
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @Column(name = "logged_in")
    private Boolean loggedIn;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Transient
    private String abbreviation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Token> tokens;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Token> refreshTokens;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PasswordReset> passwordResets;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Connection> senderConnections;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Connection> receiverConnections;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> userReviews;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> teacherReviews;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommendation> teacherRecommendations;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommendation> authorRecommendations;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecentSearch> userRecentSearches;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> userFavorites;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> teacherFavorites;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> ownerPosts;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> authorPosts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> userPostLikes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> userComments;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {

    }

    public User(
            Long id,
            String email,
            Timestamp createdAt,
            Timestamp updatedAt,
            String firstName,
            String lastName,
            String fullName,
            Role role,
            Boolean loggedIn) {
        this.id = id;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.role = role;
        this.loggedIn = loggedIn;

    }

    public User(
            String email,
            String firstName,
            String lastName,
            String fullName,
            Role role,
            Boolean loggedIn,
            Profile profile,
            String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.role = role;
        this.loggedIn = loggedIn;
        this.profile = profile;
        this.password = password;
    }

    public String getAbbreviation() {
        return firstName.substring(0, 1).toUpperCase() + lastName.substring(0, 1).toUpperCase();
    }

    public List<PostLike> getUserPostLikes() {

        return userPostLikes;
    }

    public Long getId() {
        return id;
    }

    public List<Comment> getUserComments() {
        return userComments;
    }

    public List<RecentSearch> getUserRecentSearches() {
        return userRecentSearches;
    }

    public List<Recommendation> getAuthorRecommendations() {
        return authorRecommendations;
    }

    public List<Recommendation> getTeacherRecommendations() {
        return teacherRecommendations;
    }

    public List<Favorite> getUserFavorites() {
        return userFavorites;
    }

    public List<Post> getOwnerPosts() {
        return ownerPosts;
    }

    public List<Post> getAuthorPosts() {
        return authorPosts;
    }

    public List<Favorite> getTeacherFavorites() {
        return teacherFavorites;
    }

    public List<Review> getUserReviews() {
        return userReviews;
    }

    public List<Review> getTeacherReviews() {
        return teacherReviews;
    }

    public List<Connection> getSenderConnections() {
        return senderConnections;
    }

    public List<Connection> getReceiverConnections() {
        return receiverConnections;
    }

    public List<PasswordReset> getPasswordResets() {
        return passwordResets;
    }

    public List<Token> getRefreshTokens() {
        return refreshTokens;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void setUserReviews(List<Review> userReviews) {
        this.userReviews = userReviews;
    }

    public void setUserComments(List<Comment> userComments) {
        this.userComments = userComments;
    }

    public void setUserPostLikes(List<PostLike> userPostLikes) {
        this.userPostLikes = userPostLikes;
    }

    public void setTeacherReviews(List<Review> teacherReviews) {
        this.teacherReviews = teacherReviews;
    }

    public void setSenderConnections(List<Connection> senderConnections) {
        this.senderConnections = senderConnections;
    }

    public void setReceiverConnections(List<Connection> receiverConnections) {
        this.receiverConnections = receiverConnections;
    }

    public void setPasswordResets(List<PasswordReset> passwordResets) {
        this.passwordResets = passwordResets;
    }

    public void setRefreshTokens(List<Token> refreshTokens) {
        this.refreshTokens = refreshTokens;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setUserRecentSearches(List<RecentSearch> userRecentSearches) {
        this.userRecentSearches = userRecentSearches;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUserFavorites(List<Favorite> userFavorites) {
        this.userFavorites = userFavorites;
    }

    public void setTeacherFavorites(List<Favorite> teacherFavorites) {
        this.teacherFavorites = teacherFavorites;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorRecommendations(List<Recommendation> authorRecommendations) {
        this.authorRecommendations = authorRecommendations;
    }

    public void setTeacherRecommendations(List<Recommendation> teacherRecommendations) {
        this.teacherRecommendations = teacherRecommendations;
    }

    public void setOwnerPosts(List<Post> ownerPosts) {
        this.ownerPosts = ownerPosts;
    }

    public void setAuthorPosts(List<Post> authorPosts) {
        this.authorPosts = authorPosts;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
