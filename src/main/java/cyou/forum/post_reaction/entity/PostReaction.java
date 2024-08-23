package cyou.forum.post_reaction.entity;

import cyou.audit.AuditEntity;
import cyou.forum.post.entity.Post;
import cyou.enums.Reaction;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostReaction extends AuditEntity {

    @Enumerated(EnumType.STRING)
    private Reaction reaction;

    @ManyToOne
    private Post post;

}
