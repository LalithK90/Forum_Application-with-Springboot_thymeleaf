package cyou.forum.post_reaction.dao;

import cyou.forum.post.entity.Post;
import cyou.forum.post_reaction.entity.PostReaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReactionDao extends JpaRepository<PostReaction, Long> {
    PostReaction findByPostAndCreatedBy(Post post, String username);
}
