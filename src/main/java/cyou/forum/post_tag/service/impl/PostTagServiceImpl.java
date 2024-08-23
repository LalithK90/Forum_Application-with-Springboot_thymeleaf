package cyou.forum.post_tag.service.impl;


import cyou.forum.post.service.PostService;
import cyou.forum.post_tag.dao.PostTagDao;
import cyou.forum.post_tag.entity.PostTag;
import cyou.forum.post_tag.service.PostTagService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostTagServiceImpl implements PostTagService {

    private final PostTagDao postTagDao;

    public List<PostTag> findAll() {
        return postTagDao.findAll();
    }

    public PostTag getPostById(Long id) {
        return postTagDao.getReferenceById(id);
    }

    public PostTag savePost(PostTag post) {
        return postTagDao.save(post);
    }

    public void deletePost(Long id) {
        postTagDao.deleteById(id);
    }

}

