package cyou.forum.post_comment_reaction.dao;

import cyou.forum.post_comment_reaction.entity.ReactionComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionPostOrCommentDao extends JpaRepository<ReactionComment, Long> {}
