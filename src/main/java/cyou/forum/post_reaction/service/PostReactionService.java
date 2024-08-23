package cyou.forum.post_reaction.service;


import cyou.forum.post.entity.Post;
import cyou.forum.post_reaction.entity.PostReaction;

public interface PostReactionService {

    PostReaction findByPostAndCreatedBy(Post post, String username);

    PostReaction persist(PostReaction postReaction);

    void deleteByPostReaction(PostReaction postReaction);
}
