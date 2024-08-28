package cyou.forum.post.service.impl;

import cyou.helps.CommonService;
import cyou.forum.post.dao.PostDao;
import cyou.forum.post.entity.Post;
import cyou.forum.post.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostDao postDao;
    private final CommonService commonService;



    public Page<Post> getAllPosts(Pageable pageable) {
        return postDao.findAll(pageable);
    }

    public Post persist(Post post) {
        if (post.getId() == null) {
            post.setNumber(commonService.generateUniqCode(10));
        }
        return postDao.save(post);
    }

    @Transactional(readOnly = true)
    public Post findByNumber(String number) {
        return postDao.findByNumber(number);
    }

    public boolean deleteByNumber(String number) {
        var post = postDao.findByNumber(number);
        if (number == null) {
            return false;
        } else {
            var postTags = post.getPostTags();
            if (postTags != null) {
                post.setPostTags(new HashSet<>());
                postDao.save(post);
            }
            postDao.delete(post);
            return true;
        }
    }
}

