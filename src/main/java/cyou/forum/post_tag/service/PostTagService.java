package cyou.forum.post_tag.service;


import cyou.forum.post_tag.entity.PostTag;

import java.util.List;


public interface PostTagService {

    List<PostTag> findAll();

    PostTag getPostById(Long id);

    PostTag savePost(PostTag post);

    void deletePost(Long id);
}

