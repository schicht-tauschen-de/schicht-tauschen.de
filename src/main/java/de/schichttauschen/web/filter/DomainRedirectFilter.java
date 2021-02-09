package de.schichttauschen.web.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DomainRedirectFilter implements Filter {
    @Value("${app.domain:#{null}}")
    private String appDomain;

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if (appDomain != null && !httpRequest.getServerName().equalsIgnoreCase(appDomain)) {
            httpResponse.sendRedirect(
                    "https://"
                            + appDomain
                            + httpRequest.getRequestURI()
                            + (StringUtils.isNotEmpty(httpRequest.getQueryString()) ? "?" + httpRequest.getQueryString() : Strings.EMPTY));
        } else filterChain.doFilter(httpRequest, httpResponse);
    }
}
