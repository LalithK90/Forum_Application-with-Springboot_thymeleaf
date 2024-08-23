package cyou.forum.post_comment_reaction.service;

import cyou.forum.post_comment_reaction.entity.ReactionComment;


public interface ReactionPostOrCommentService {
    ReactionComment saveReaction(ReactionComment reactionComment);
}
