package cyou.forum.main_form_app.dto;

import cyou.enums.Reaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostViewDto {
    private String title, number, content, postOwner;
    private int postViewCount;
    private Map<Reaction, Long> postReactions;
}
