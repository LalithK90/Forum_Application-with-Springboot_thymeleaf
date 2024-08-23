package cyou.forum.service;


import cyou.forum.dao.PostTagRepository;
import cyou.forum.entity.PostTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTagService {
    @Autowired
    private PostTagRepository postTagRepository;

    public List<PostTag> findAll() {
        return postTagRepository.findAll();
    }

    public PostTag getPostById(Long id) {
        return postTagRepository.getReferenceById(id);
    }

    public PostTag savePost(PostTag post) {
        return postTagRepository.save(post);
    }

    public void deletePost(Long id) {
        postTagRepository.deleteById(id);
    }

}

