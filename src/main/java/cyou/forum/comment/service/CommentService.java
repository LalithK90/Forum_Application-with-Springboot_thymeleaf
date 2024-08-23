package cyou.forum.comment.service;

import cyou.forum.comment.dao.CommentDao;
import cyou.forum.comment.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface CommentService {

     Comment saveComment(Comment comment);
     void deleteComment(Long id);
}
