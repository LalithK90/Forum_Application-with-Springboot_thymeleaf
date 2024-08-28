package cyou.helps;

import cyou.forum.comment.service.CommentService;
import cyou.forum.comment.service.impl.CommentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CommonService commonService() {
        return new CommonService();
    }

}
