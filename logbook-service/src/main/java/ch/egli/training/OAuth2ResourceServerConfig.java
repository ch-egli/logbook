package ch.egli.training;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId("Logbook_Resources");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
        http.headers()
				.frameOptions()  // Set X-Frame-Options in order to open iFrame to download an Excel file...
				.disable()
				.and()
            .requestMatchers()
                .antMatchers("/v1/**")
                .and()
            .authorizeRequests()
                .antMatchers("/v1/public/lastworkouts").permitAll()
                .antMatchers("/v1/users/**").authenticated();
	}
	
}
