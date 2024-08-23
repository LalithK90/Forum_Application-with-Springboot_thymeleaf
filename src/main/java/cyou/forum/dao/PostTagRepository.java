package cyou.forum.dao;

import cyou.forum.entity.Post;
import cyou.forum.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

}



