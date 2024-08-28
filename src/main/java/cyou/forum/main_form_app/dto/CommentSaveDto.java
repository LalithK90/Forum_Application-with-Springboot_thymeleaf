package cyou.forum.main_form_app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentSaveDto {
    private Long commentId;
    private String number;
    private boolean edit;
    @NotNull
    private String content;
}
