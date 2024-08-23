package cyou.forum.post_reaction.service.impl;

import cyou.forum.post.entity.Post;
import cyou.forum.post_reaction.dao.PostReactionDao;
import cyou.forum.post_reaction.entity.PostReaction;
import cyou.forum.post_reaction.service.PostReactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostReactionServiceImpl implements PostReactionService {

    private final PostReactionDao postReactionDao;


    public PostReaction findByPostAndCreatedBy(Post post, String username) {
        return postReactionDao.findByPostAndCreatedBy(post,username);
    }

    public PostReaction persist(PostReaction postReaction) {
        return postReactionDao.save(postReaction);
    }

    public void deleteByPostReaction(PostReaction postReaction) {
        postReactionDao.delete(postReaction);
    }
}
