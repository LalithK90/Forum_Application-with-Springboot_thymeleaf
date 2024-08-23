package cyou.forum.entity;

import cyou.forum.audit.AuditEntity;
import cyou.forum.entity.enums.Reaction;
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
public class ReactionPost extends AuditEntity {

    @Enumerated(EnumType.STRING)
    private Reaction reaction;

    @ManyToOne
    private Post post;

}
