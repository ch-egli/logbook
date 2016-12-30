package ch.egli.training.config;

import ch.egli.training.util.LogbookRequestLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * Request logging filter configuration.
 * Permits to log certain requests based on users and request uri.
 *
 * @author Christian Egli
 * @since 12/30/16.
 */
@Configuration
public class LoggingFilterConfig {

    @Bean
    public Filter logFilter() {
        LogbookRequestLoggingFilter filter = new LogbookRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setIncludeClientInfo(true);
        return filter;
    }
}
