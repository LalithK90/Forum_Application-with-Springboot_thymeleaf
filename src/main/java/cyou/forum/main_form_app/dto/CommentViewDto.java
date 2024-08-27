package cyou.forum.main_form_app.dto;

import cyou.enums.Reaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentViewDto {
    private long id, parentCommentId;
    private String postNumber, content, createdBy, createdAt;
    private boolean editable;
    private List<CommentViewDto> replies;
    private Map<Reaction, Long> commentReactions;
}
