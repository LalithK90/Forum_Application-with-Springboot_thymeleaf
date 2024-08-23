package cyou.forum.dao;

import cyou.forum.entity.ReactionComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionPostOrCommentRepository extends JpaRepository<ReactionComment, Long> {}
