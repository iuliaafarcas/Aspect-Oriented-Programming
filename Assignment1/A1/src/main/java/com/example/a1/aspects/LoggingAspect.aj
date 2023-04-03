package com.example.a1.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public aspect LoggingAspect {
    pointcut logging(): execution(public * com.example.a1.service.DeliveryService.*(..)) && !within(LoggingAspect);

    private static final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    before(): logging() {
        MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
        logger.info(buildLog("entered", methodSignature, thisJoinPoint));
    }

    after() returning: logging() {
        MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
        logger.info(buildLog("exited successfully", methodSignature, thisJoinPoint));
    }

    after() throwing(Throwable ex): logging() {
        MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
        logger.severe(methodSignature.getName() + "(): Error " + ex.getMessage());
    }

    String buildLog(String methodOperation, MethodSignature methodSignature, JoinPoint joinPoint) {

        StringBuilder log = new StringBuilder("METHOD " + methodSignature.getName() + ": " + methodOperation + " ");
        for (int i = 0; i < methodSignature.getParameterNames().length; i++) {
            log.append(methodSignature.getParameterNames()[i]).append(" ").append(joinPoint.getArgs()[i].toString()).append("; ");

        }
        return log.toString();
    }
}
