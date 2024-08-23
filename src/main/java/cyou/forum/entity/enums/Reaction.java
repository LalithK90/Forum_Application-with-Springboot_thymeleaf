package cyou.forum.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Reaction {

    LIKE("Like"),
    DIS_LIKE("Dislike"),
    CELEBRATE("Celebrate"),
    SUPPORTIVE("Supportive"),
    LOVE("Love"),
    INSIGHTFUL("Insightful");

    private final String reaction;
}
