package cyou.forum.main_form_app.dto;

import cyou.enums.Reaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentViewDto {
    private String content;
    private long post, parentComment;
    private Map<Reaction, Long> commentReactions;
}
