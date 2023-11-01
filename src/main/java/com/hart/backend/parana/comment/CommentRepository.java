package com.hart.backend.parana.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findTop5ByUserIdAndPostIdOrderByIdDesc(Long userId, Long postId);

}
