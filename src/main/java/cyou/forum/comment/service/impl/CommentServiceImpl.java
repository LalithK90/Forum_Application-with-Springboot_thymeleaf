package cyou.forum.comment.service.impl;

import cyou.forum.comment.dao.CommentDao;
import cyou.forum.comment.entity.Comment;
import cyou.forum.comment.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    public Comment saveComment(Comment comment) {
        return commentDao.save(comment);
    }

    public void deleteComment(Long id) {
        commentDao.deleteById(id);
    }

    // Additional methods
}
