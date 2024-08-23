package cyou.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String title, createdBy,createdAt,profileUrl,number;
    private int reactCount, viewCount;
    private boolean editable;
}
