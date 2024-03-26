package th.co.ais.mimo.debt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "th.co.ais.mimo.debt.*")
public class JpaConfig {
}
