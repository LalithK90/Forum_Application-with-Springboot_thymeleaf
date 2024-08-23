package cyou.forum.comment.dao;

import cyou.forum.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment, Long> {}
