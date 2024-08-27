package cyou.forum.comment_reaction.service.impl;

import cyou.forum.comment.entity.Comment;
import cyou.forum.comment_reaction.dao.CommentReactionDao;
import cyou.forum.comment_reaction.entity.CommentReaction;
import cyou.forum.comment_reaction.service.CommentReactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentReactionServiceImpl implements CommentReactionService {

    private final CommentReactionDao commentReactionDao;

    public CommentReaction persist(CommentReaction commentReaction) {
        return commentReactionDao.save(commentReaction);
    }

    public void deleteByCommentReaction(CommentReaction commentReaction) {
        commentReactionDao.delete(commentReaction);
    }

    public CommentReaction findByCommentAndCreatedBy(Comment comment, String username) {
        return commentReactionDao.findByCommentAndCreatedBy(comment,username);
    }

}
