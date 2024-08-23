package cyou.forum.post_comment_reaction.service.impl;

import cyou.forum.post_comment_reaction.dao.ReactionPostOrCommentDao;
import cyou.forum.post_comment_reaction.entity.ReactionComment;
import cyou.forum.post_comment_reaction.service.ReactionPostOrCommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReactionPostOrCommentServiceImpl implements ReactionPostOrCommentService {

    private final ReactionPostOrCommentDao reactionPostOrCommentDao;

    public ReactionComment saveReaction(ReactionComment reactionComment) {
        return reactionPostOrCommentDao.save(reactionComment);
    }

}
