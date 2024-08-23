package cyou.forum.comment_reaction.service;

import cyou.forum.comment_reaction.entity.CommentReaction;


public interface CommentReactionService {
    CommentReaction saveReaction(CommentReaction commentReaction);
}
