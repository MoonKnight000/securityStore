package uz.softex.securitystore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import uz.softex.securitystore.workers.entity.Workers;

@Configuration
@EnableJpaAuditing
public class CreatedConfig {
    @Bean
    AuditorAware<Workers> auditorAware() {
        return new CreatedBy();
    }
}
