// $Id\$
package de.schichttauschen.web.aspect;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

@Aspect
@Component
public class RateLimitedAspect {
    Table<String, String, Bucket> buckets = HashBasedTable.create();

    @Around("@annotation(rateLimited)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, RateLimited rateLimited) throws Throwable {
        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes());
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        String remoteAddr = request.getRemoteAddr();
        String endpoint = joinPoint.getSignature().toString();

        Bucket bucket = buckets.get(remoteAddr, endpoint);
        if (bucket == null) {
            bucket = createNewBucket(rateLimited.requests(), rateLimited.interval());
            buckets.put(remoteAddr, endpoint, bucket);
        }

        if (bucket.tryConsume(1)) {
            return joinPoint.proceed();
        } else {
            if (response != null) {
                response.setContentType("text/plain");
                response.setStatus(429);
                response.getWriter().append("Too many requests");
            }
            return null;
        }
    }

    private Bucket createNewBucket(Long requests, RateLimited.Interval interval) {
        Duration duration;
        switch (interval) {
            default:
            case Seconds:
                duration = Duration.ofSeconds(1);
                break;
            case Minutes:
                duration = Duration.ofMinutes(1);
                break;
            case Hours:
                duration = Duration.ofHours(1);
                break;
        }
        Bandwidth limit = Bandwidth.simple(requests, duration);
        return Bucket4j.builder().addLimit(limit).build();
    }
}
