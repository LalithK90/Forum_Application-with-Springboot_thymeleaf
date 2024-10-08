package cyou.forum.comment.entity;

import cyou.audit.AuditEntity;
import cyou.forum.comment_reaction.entity.CommentReaction;
import cyou.forum.post.entity.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends AuditEntity {

    @NotNull
    @Column(columnDefinition = "LONGTEXT")
    @Lob
    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentReaction> commentReactions;


}
