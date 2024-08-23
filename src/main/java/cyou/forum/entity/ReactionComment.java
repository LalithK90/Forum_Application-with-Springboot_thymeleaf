package cyou.forum.entity;

import cyou.forum.audit.AuditEntity;
import cyou.forum.entity.enums.Reaction;
import jakarta.persistence.*;
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
