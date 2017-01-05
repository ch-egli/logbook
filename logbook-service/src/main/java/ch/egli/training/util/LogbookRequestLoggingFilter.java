package ch.egli.training.util;

import ch.egli.training.model.Requestlog;
import ch.egli.training.repository.RequestlogRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Request logging filter:
 * Permits to log certain requests based on users and request uri.
 *
 * @author Christian Egli
 * @since 12/30/16.
 */
public class LogbookRequestLoggingFilter extends CommonsRequestLoggingFilter {
    private static final Logger LOGGER = LogManager.getLogger(LogbookRequestLoggingFilter.class.getName());

    private final static String DEFAULT_LOG_USER_1 = "maex";
    private final static String DEFAULT_LOG_USER_2 = "jojo";
    private final static String DEFAULT_LOG_USER_3 = "chrigu";

    private final static String LOG_URI_1= "/v1/usrs/";
    private final static String LOG_URI_2= "/v1/users/";

    private final static String LOG__NOT_URI_1= "/v1/users/all/workouts?page=0";
    private final static String LOG__NOT_URI_2= "/v1/users/all/status?page=0";



    private List<String> logUsers;
    private List<String> logUris;
    private List<String> logNotUris;

    @Autowired
    private RequestlogRepository requestlogRepository;

    public LogbookRequestLoggingFilter() {
        logUsers = new ArrayList<>();
        logUsers.add(DEFAULT_LOG_USER_1);
        logUsers.add(DEFAULT_LOG_USER_2);
        logUsers.add(DEFAULT_LOG_USER_3);

        logUris = new ArrayList<>();
        logUris.add(LOG_URI_1);
        logUris.add(LOG_URI_2);

        logNotUris = new ArrayList<>();
        logNotUris.add(LOG__NOT_URI_1);
        logNotUris.add(LOG__NOT_URI_2);
    }

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return super.shouldLog(request);
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        super.beforeRequest(request, message);

        // only log for the given list of users
        final String user = request.getRemoteUser();
        if (logUsers.contains(user)) {
            String uri = request.getRequestURI();
            String queryString = request.getQueryString();
            if (queryString != null) {
                uri = uri + '?' + queryString;
            }

            // do not log if uri is in this list
            for (String logNotUri : logNotUris) {
                if (uri.contains(logNotUri)) {
                    return;
                }
            }

            // log if uri is in this list
            for (String logUri : logUris) {
                if (uri.contains(logUri)) {
                    logRequest(user, logUri, message);
                    break;
                }
            }
        }
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        // do not log after the request...
    }

    private void logRequest(String user, String uriFilter, String logMessage) {
        //LOGGER.debug("#### user: {}, uriFilter: {}, logMessage: {}", user, uriFilter, logMessage);
        Requestlog requestlog = new Requestlog();
        Date date = new Date();
        requestlog.setDatum(new Timestamp(date.getTime()));
        requestlog.setBenutzer(user);
        requestlog.setUrifilter(uriFilter);
        requestlog.setMessage(logMessage);
        requestlogRepository.save(requestlog);
    }

    public List<String> getLogUsers() {
        return logUsers;
    }

    public void setLogUsers(List<String> logUsers) {
        this.logUsers = logUsers;
    }

    public List<String> getLogUris() {
        return logUris;
    }

    public void setLogUris(List<String> logUris) {
        this.logUris = logUris;
    }

    public List<String> getLogNotUris() {
        return logNotUris;
    }

    public void setLogNotUris(List<String> logNotUris) {
        this.logNotUris = logNotUris;
    }
}
