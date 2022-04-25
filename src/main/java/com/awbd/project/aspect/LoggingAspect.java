package com.awbd.project.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final ObjectMapper mapper;

    @Pointcut("within(com.awbd.project.service..*) || within(com.awbd.project.controller..*)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void logMethodCall(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        try {
            log.info("Method call: {}, timestamp: {}, arguments: {} ",
                    signature.getMethod().getDeclaringClass() + "." + signature.getMethod().getName(),
                    LocalDateTime.now(),
                    mapper.writeValueAsString(signature.getParameterNames()));
        } catch (JsonProcessingException e) {
            log.warn(e.getMessage(), e);
        }
    }
}
