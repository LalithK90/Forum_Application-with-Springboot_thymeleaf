package cyou.forum.post_comment_reaction.entity;

import cyou.audit.AuditEntity;
import cyou.forum.comment.entity.Comment;
import cyou.forum.post_comment_reaction.entity.enums.Reaction;
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
public class ReactionComment extends AuditEntity {

    @Enumerated(EnumType.STRING)
    private Reaction reaction;

    @ManyToOne
    private Comment comment;

}
