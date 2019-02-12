package com.jrmcdonald.podcasts.app.util;

import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * URL Utilities class
 * 
 * @author Jamie McDonald
 */
public class UrlHelper {

    private static final Logger logger = LoggerFactory.getLogger(UrlHelper.class);

    /**
     * Prefix a specified URL fragment with the base URL from the current request.
     * 
     * @param url the URL fragment to prefix
     * @return the prefixed URL
     */
    public static String prefixWithBaseUrl(final String url) {
        return getBaseUrl() + url;
    }

    /**
     * Retrieve the base URL from the current request i.e. https://www.domain.com/
     * 
     * @return the base URL
     */
    public static String getBaseUrl() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = sra.getRequest();

        String url = "";

        try {
            URL requestURL = new URL(req.getRequestURL().toString());
            String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
            url = requestURL.getProtocol() + "://" + requestURL.getHost() + port;
        } catch (MalformedURLException e) {
            logger.error("Error occurred whilst retrieving base URL: {}", e);
        }

        return url;
    }
}
