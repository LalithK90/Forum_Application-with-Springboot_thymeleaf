package cyou.forum.aspects;


import cyou.helps.CommonService;
import cyou.forum.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class CommentAspect {
private final CommonService commonService;
    @AfterReturning(value = "execution(* cyou.forum.comment.service.CommentService.persistOne(..))")
    public void logAfterReturning(JoinPoint joinPoint) {
        Comment comment = (Comment) joinPoint.getArgs()[0];
   commonService.printAttributesInObject(comment);
    }
}
