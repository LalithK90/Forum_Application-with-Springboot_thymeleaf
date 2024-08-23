package cyou.forum.service;

import cyou.forum.dao.ReactionPostOrCommentRepository;
import cyou.forum.entity.ReactionComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactionPostOrCommentService {
    @Autowired
    private ReactionPostOrCommentRepository reactionPostOrCommentRepository;

    public ReactionComment saveReaction(ReactionComment reactionComment) {
        return reactionPostOrCommentRepository.save(reactionComment);
    }

    // Additional methods
}
