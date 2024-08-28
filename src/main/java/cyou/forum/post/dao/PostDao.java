package cyou.forum.post.dao;

import cyou.forum.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostDao extends JpaRepository<Post, Long> {
    //    @Query(value = "select * from post where number=?1 ",nativeQuery = true)
    @Query("SELECT p FROM Post p WHERE p.number = :number")
    Post findByNumber(@Param("number") String number);
//    List<Post> findByOrderByViewCountDesc();
//    List<Post> findByOrderByCommentsSizeDesc();
//    List<Post> findByOrderByReactionsSizeDesc();
//    List<Post> findByTagsContaining(String tag);

}



