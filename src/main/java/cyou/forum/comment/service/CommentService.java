package cyou.forum.comment.service;

import cyou.forum.comment.entity.Comment;
import cyou.forum.main_form_app.dto.CommentViewDto;
import cyou.forum.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommentService {
    Page<CommentViewDto> findByPost(Post post, int page,int size);

     Comment persist(Comment comment);

    Comment findById(Long commentId);

    Comment findByIdAndCreatedBy(Long commentId, String username);

    boolean deleteByComment(Comment comment);
}
