package org.nure.julia;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:application-database.properties")
@EnableTransactionManagement
@EnableJpaRepositories("org.nure.julia.repository")
public class DatabaseConfiguration {
}
