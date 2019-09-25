package org.nure.julia;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-database.properties")
public class DatabaseConfiguration {
}
