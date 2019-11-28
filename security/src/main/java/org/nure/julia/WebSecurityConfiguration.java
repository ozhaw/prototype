package org.nure.julia;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.List;

@EnableWebSecurity
@PropertySource({"classpath:application-security.properties"})
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${default.security.includes}")
    private String[] protectedUrls;

    @Value("${default.security.context}")
    private String securityContextPath;

    private final DiscoveryClient discoveryClient;

    @Autowired
    public WebSecurityConfiguration(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
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
    public String authenticationServiceURL() {
        List<ServiceInstance> authInstances = discoveryClient.getInstances("authentication-service");
        return StringUtils.defaultIfBlank(gatewayURL(), authInstances == null || authInstances.isEmpty()
                ? StringUtils.EMPTY
                : authInstances.get(0).getUri() + securityContextPath);
    }

    @Bean
    public String gatewayURL() {
        List<ServiceInstance> gatewayInstances = discoveryClient.getInstances("gateway-service");
        return gatewayInstances == null || gatewayInstances.isEmpty()
                ? StringUtils.EMPTY
                : gatewayInstances.get(0).getUri() + securityContextPath;
    }

    @Bean
    public FilterRegistrationBean securityFilterRegistration() {
        FilterRegistrationBean<SecurityWebFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new SecurityWebFilter(authenticationServiceURL()));
        registration.addUrlPatterns(protectedUrls);
        registration.setName("securityWebFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

}
