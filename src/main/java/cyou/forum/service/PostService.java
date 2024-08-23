package cyou.forum.service;

import cyou.forum.CommonService;
import cyou.forum.dao.PostDao;
import cyou.forum.entity.Post;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

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

    public Post findById(Long id) {
        return postDao.getReferenceById(id);
    }

    public Post findByNumber(String number) {
        return postDao.findByNumber(number);
    }

    public boolean deleteByNumber(String number) {
        var post = postDao.findByNumber(number);
        if (number == null) {
            return false;
        } else {
            postDao.delete(post);
            return true;
        }
    }
}

