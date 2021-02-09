package de.schichttauschen.web.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class HttpRequestService {
    public HttpServletRequest get() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
                .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
                .map(ServletRequestAttributes::getRequest).orElse(null);
    }

    private boolean isNonStandardPort(int port) {
        switch (port) {
            case 80:
            case 443:
                return false;
            default:
                return true;
        }
    }

    public String getFullHostname() {
        var request = get();
        return request.getScheme() + "://" +
                request.getServerName() +
                (isNonStandardPort(request.getServerPort()) ? ":" + request.getServerPort() : "");
    }
}
