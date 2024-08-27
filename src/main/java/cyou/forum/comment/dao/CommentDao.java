package cyou.forum.comment.dao;

import cyou.forum.comment.entity.Comment;
import cyou.forum.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentDao extends JpaRepository<Comment, Long> {

    Page<Comment> findByPost(Post post, Pageable pageable);

    Comment findByIdAndCreatedBy(Long commentId, String username);
}
