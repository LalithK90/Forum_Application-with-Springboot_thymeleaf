package cyou.forum.main_form_app.mappers;

import cyou.enums.Reaction;
import cyou.forum.comment.entity.Comment;
import cyou.forum.comment_reaction.entity.CommentReaction;
import cyou.forum.main_form_app.dto.CommentViewDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    // Convert Comment entity to CommentDTO
    public CommentViewDto toDto(Comment comment) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        CommentViewDto dto = new CommentViewDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(localDateTimeToString(comment.getCreatedAt()));
        dto.setCreatedBy(comment.getCreatedBy());
        dto.setEditable(username.equals(comment.getCreatedBy()));
        if (comment.getParentComment() != null) {
            dto.setParentCommentId(comment.getParentComment().getId());
        } else {
            dto.setPostNumber(comment.getPost().getNumber());
        }

        dto.setCommentReactions(countReactionPost(comment.getCommentReactions()));

        // Convert replies to DTOs recursively
        if (comment.getReplies() != null) {
            List<CommentViewDto> replyDtos = comment.getReplies().stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
            dto.setReplies(replyDtos);
        }
        return dto;
    }

    private Map<Reaction, Long> allReactionList() {
        Map<Reaction, Long> reactionCounts = new EnumMap<>(Reaction.class);
        for (Reaction reaction : Reaction.values()) {
            reactionCounts.put(reaction, 0L);
        }
        return reactionCounts;
    }

    private Map<Reaction, Long> countReactionPost(List<CommentReaction> reactions) {
        Map<Reaction, Long> reactionCounts = allReactionList();
        if (reactions != null) {
            reactionCounts.putAll(reactions.stream().filter(reaction -> reaction.getReaction() != null)
                    .collect(Collectors.groupingBy(CommentReaction::getReaction, Collectors.counting())));

        }
        return reactionCounts;
    }

    private String localDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

}
