package com.hart.backend.parana.post;

import java.util.List;

import com.hart.backend.parana.user.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findTop5ByAuthorIdAndOwnerIdOrderByIdDesc(Long authorId, Long ownerId);
}
