package cyou.forum.comment_reaction.dao;

import cyou.forum.comment.entity.Comment;
import cyou.forum.comment_reaction.entity.CommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReactionDao extends JpaRepository<CommentReaction, Long> {
 CommentReaction findByCommentAndCreatedBy(Comment comment, String username);
}
