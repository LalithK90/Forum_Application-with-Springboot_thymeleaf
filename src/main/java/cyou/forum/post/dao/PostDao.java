package cyou.forum.post.dao;

import cyou.forum.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDao extends JpaRepository<Post, Long> {
    Post findByNumber(String number);
//    List<Post> findByOrderByViewCountDesc();
//    List<Post> findByOrderByCommentsSizeDesc();
//    List<Post> findByOrderByReactionsSizeDesc();
//    List<Post> findByTagsContaining(String tag);

}



