package cyou.forum.post_tag.entity;

import cyou.forum.post.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tag;

    @Column(unique = true)
    private String adCode;

    @ManyToMany(mappedBy = "postTags")
    private Set<Post> posts = new HashSet<>();

}