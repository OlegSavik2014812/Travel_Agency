package com.epam.core.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * The type Performance logger.
 */
@Aspect
@Component
public class PerformanceLogger {
    private static final Logger LOGGER = LogManager.getLogger(PerformanceLogger.class);

    /**
     * Log performance object.
     *
     * @param point the point
     * @return the object
     */
    @Around("@annotation(com.epam.core.log.ExecutionTime)")
    public Object logPerformance(ProceedingJoinPoint point) {
        Object object = null;
        try {
            long start = System.nanoTime();
            object = point.proceed();
            long end = System.nanoTime();
            LOGGER.info("The performance of [" + point.getSignature() +
                    "] method is: " + (end - start) + " milliseconds.");
            return object;
        } catch (Throwable e) {
            LOGGER.error("Exception: " + e);
        }
        return object;
    }
}
