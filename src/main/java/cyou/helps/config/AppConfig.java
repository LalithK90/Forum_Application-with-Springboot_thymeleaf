package cyou.helps.config;

import cyou.helps.CommonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CommonService commonService() {
        return new CommonService();
    }

}
