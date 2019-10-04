package org.nure.julia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@PropertySource({"classpath:application-security.properties"})
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${default.security.includes}")
    private String[] protectedUrls;

    private final SecurityWebFilter securityWebFilter;

    @Autowired
    public WebSecurityConfiguration(SecurityWebFilter securityWebFilter) {
        this.securityWebFilter = securityWebFilter;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Bean
    public FilterRegistrationBean securityFilterRegistration() {
        FilterRegistrationBean<SecurityWebFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(securityWebFilter);
        registration.addUrlPatterns(protectedUrls);
        registration.setName("securityWebFilter");
        registration.setOrder(1);
        return registration;
    }

}
