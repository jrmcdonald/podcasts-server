package com.jrmcdonald.podcasts.app.util;

import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * UrlHelper
 */
public class UrlHelper {

    private static final Logger logger = LoggerFactory.getLogger(UrlHelper.class);

    public static String prefixWithBaseUrl(final String url) {
        return getBaseUrl() + url;
    }

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
