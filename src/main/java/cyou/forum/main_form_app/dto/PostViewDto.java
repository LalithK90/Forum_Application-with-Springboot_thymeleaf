package cyou.forum.main_form_app.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostViewDto {
    private String title, number,content;
//    todo need to send reactions, comments, and comment reaction
}
