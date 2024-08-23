package cyou.forum.comment_reaction.entity;

import cyou.audit.AuditEntity;
import cyou.enums.Reaction;
import cyou.forum.comment.entity.Comment;
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
public class CommentReaction extends AuditEntity {

    @Enumerated(EnumType.STRING)
    private Reaction reaction;

    @ManyToOne
    private Comment comment;

}
