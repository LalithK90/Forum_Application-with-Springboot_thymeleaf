package cyou.forum.post.service;

import cyou.forum.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostService {

    Page<Post> getAllPosts(Pageable pageable);

    Post persist(Post post);

    Post findByNumber(String number);

    boolean deleteByNumber(String number);

}

