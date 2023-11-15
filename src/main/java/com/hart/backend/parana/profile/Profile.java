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

    @Column(name = "about_lesson", length = 400)
    private String aboutLesson;

    @Column(name = "city")
    private String city;

    @Column(name = "first_lesson_free")
    private Boolean firstLessonFree;

    @Column(name = "home_mountain")
    private String homeMountain;

    @Column(name = "per_hour")
    private String perHour;

    @Column(name = "stance")
    private String stance;

    @Column(name = "state")
    private String state;

    @Column(name = "tags")
    private String tags;

    @Column(name = "terrain")
    private String terrain;

    @Column(name = "travel_up_to")
    private String travelUpTo;

    @Column(name = "years_snowboarding")
    private Integer yearsSnowboarding;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

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
            String aboutLesson,
            String city,
            Boolean firstLessonFree,
            String homeMountain,
            String perHour,
            String stance,
            String state,
            String tags,
            String terrain,
            String travelUpTo,
            Integer yearsSnowboarding,
            Double latitude,
            Double longitude) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.avatarFilename = avatarFilename;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.aboutLesson = aboutLesson;
        this.city = city;
        this.firstLessonFree = firstLessonFree;
        this.homeMountain = homeMountain;
        this.perHour = perHour;
        this.stance = stance;
        this.state = state;
        this.tags = tags;
        this.terrain = terrain;
        this.travelUpTo = travelUpTo;
        this.yearsSnowboarding = yearsSnowboarding;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Long getId() {
        return id;
    }

    public String getAboutLesson() {
        return aboutLesson;
    }

    public String getBio() {
        return bio;
    }

    public User getUser() {
        return user;
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

    public String getAvatarFilename() {
        return avatarFilename;
    }

    public String getCity() {
        return city;
    }

    public String getTags() {
        return tags;
    }

    public String getState() {
        return state;
    }

    public String getStance() {
        return stance;
    }

    public String getPerHour() {
        return perHour;
    }

    public String getTravelUpTo() {
        return travelUpTo;
    }

    public String getTerrain() {
        return terrain;
    }

    public Boolean getFirstLessonFree() {
        return firstLessonFree;
    }

    public String getHomeMountain() {
        return homeMountain;
    }

    public Integer getYearsSnowboarding() {
        return yearsSnowboarding;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setStance(String stance) {
        this.stance = stance;
    }

    public void setPerHour(String perHour) {
        this.perHour = perHour;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public void setTravelUpTo(String travelUpTo) {
        this.travelUpTo = travelUpTo;
    }

    public void setHomeMountain(String homeMountain) {
        this.homeMountain = homeMountain;
    }

    public void setFirstLessonFree(Boolean firstLessonFree) {
        this.firstLessonFree = firstLessonFree;
    }

    public void setYearsSnowboarding(Integer yearsSnowboarding) {
        this.yearsSnowboarding = yearsSnowboarding;
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

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setAvatarFilename(String avatarFilename) {
        this.avatarFilename = avatarFilename;
    }

    public void setAboutLesson(String aboutLesson) {
        this.aboutLesson = aboutLesson;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;

    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
        result = prime * result + ((avatarFilename == null) ? 0 : avatarFilename.hashCode());
        result = prime * result + ((avatarUrl == null) ? 0 : avatarUrl.hashCode());
        result = prime * result + ((bio == null) ? 0 : bio.hashCode());
        result = prime * result + ((aboutLesson == null) ? 0 : aboutLesson.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((firstLessonFree == null) ? 0 : firstLessonFree.hashCode());
        result = prime * result + ((homeMountain == null) ? 0 : homeMountain.hashCode());
        result = prime * result + ((perHour == null) ? 0 : perHour.hashCode());
        result = prime * result + ((stance == null) ? 0 : stance.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((tags == null) ? 0 : tags.hashCode());
        result = prime * result + ((terrain == null) ? 0 : terrain.hashCode());
        result = prime * result + ((travelUpTo == null) ? 0 : travelUpTo.hashCode());
        result = prime * result + ((yearsSnowboarding == null) ? 0 : yearsSnowboarding.hashCode());
        result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
        result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
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
        Profile other = (Profile) obj;
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
        if (avatarFilename == null) {
            if (other.avatarFilename != null)
                return false;
        } else if (!avatarFilename.equals(other.avatarFilename))
            return false;
        if (avatarUrl == null) {
            if (other.avatarUrl != null)
                return false;
        } else if (!avatarUrl.equals(other.avatarUrl))
            return false;
        if (bio == null) {
            if (other.bio != null)
                return false;
        } else if (!bio.equals(other.bio))
            return false;
        if (aboutLesson == null) {
            if (other.aboutLesson != null)
                return false;
        } else if (!aboutLesson.equals(other.aboutLesson))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (firstLessonFree == null) {
            if (other.firstLessonFree != null)
                return false;
        } else if (!firstLessonFree.equals(other.firstLessonFree))
            return false;
        if (homeMountain == null) {
            if (other.homeMountain != null)
                return false;
        } else if (!homeMountain.equals(other.homeMountain))
            return false;
        if (perHour == null) {
            if (other.perHour != null)
                return false;
        } else if (!perHour.equals(other.perHour))
            return false;
        if (stance == null) {
            if (other.stance != null)
                return false;
        } else if (!stance.equals(other.stance))
            return false;
        if (state == null) {
            if (other.state != null)
                return false;
        } else if (!state.equals(other.state))
            return false;
        if (tags == null) {
            if (other.tags != null)
                return false;
        } else if (!tags.equals(other.tags))
            return false;
        if (terrain == null) {
            if (other.terrain != null)
                return false;
        } else if (!terrain.equals(other.terrain))
            return false;
        if (travelUpTo == null) {
            if (other.travelUpTo != null)
                return false;
        } else if (!travelUpTo.equals(other.travelUpTo))
            return false;
        if (yearsSnowboarding == null) {
            if (other.yearsSnowboarding != null)
                return false;
        } else if (!yearsSnowboarding.equals(other.yearsSnowboarding))
            return false;
        if (latitude == null) {
            if (other.latitude != null)
                return false;
        } else if (!latitude.equals(other.latitude))
            return false;
        if (longitude == null) {
            if (other.longitude != null)
                return false;
        } else if (!longitude.equals(other.longitude))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

}
