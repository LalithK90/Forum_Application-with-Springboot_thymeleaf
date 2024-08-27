package cyou.forum.comment.service.impl;

import cyou.forum.comment.dao.CommentDao;
import cyou.forum.comment.entity.Comment;
import cyou.forum.comment.service.CommentService;
import cyou.forum.main_form_app.dto.CommentViewDto;
import cyou.forum.main_form_app.mappers.CommentMapper;
import cyou.forum.post.entity.Post;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final CommentDao commentDao;

    public Comment persist(Comment comment) {
        return commentDao.save(comment);
    }

    public Comment findById(Long commentId) {
        return commentDao.getReferenceById(commentId);
    }

    public Comment findByIdAndCreatedBy(Long commentId, String username) {
        return commentDao.findByIdAndCreatedBy(commentId,username);
    }

    public boolean deleteByComment(Comment comment) {
        commentDao.delete(comment);
        return true;
    }

    public Page<CommentViewDto> findByPost(Post post, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<Comment> comments = commentDao.findByPost(post, pageable);
        return comments.map(commentMapper::toDto);
    }

}
