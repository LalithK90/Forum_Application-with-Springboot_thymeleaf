package cyou.forum.comment_reaction.service;

import cyou.forum.comment.entity.Comment;
import cyou.forum.comment_reaction.entity.CommentReaction;


public interface CommentReactionService {
    CommentReaction persist(CommentReaction commentReaction);

    void deleteByCommentReaction(CommentReaction commentReaction);

    CommentReaction findByCommentAndCreatedBy(Comment comment, String username);
}
