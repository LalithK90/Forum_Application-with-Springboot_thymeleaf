package cyou.forum.post.entity;

import cyou.audit.AuditEntity;
import cyou.forum.comment.entity.Comment;
import cyou.forum.post_reaction.entity.PostReaction;
import cyou.forum.post_tag.entity.PostTag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends AuditEntity {

    private String number;

    @NotNull
    private String title;

    @NotNull
    @Column(columnDefinition = "LONGTEXT")
    @Lob
    private String content;

    private int viewCount = 0;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "post_tag_id"))
    private Set<PostTag> postTags = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostReaction> postReactions = new ArrayList<>();


}

