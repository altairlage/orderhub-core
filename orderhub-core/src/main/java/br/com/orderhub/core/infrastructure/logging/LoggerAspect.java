package br.com.orderhub.core.infrastructure.logging;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Aspect
@Component
@Log4j2
public class LoggerAspect {

    @Around("@annotation(LoggerBean)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LoggerBean loggerBean = signature.getMethod().getAnnotation(LoggerBean.class);

        Map<String, Object> args = new LinkedHashMap<>();
        String[] names = signature.getParameterNames();
        for (int i = 0; i < names.length; i++) {
            args.put(names[i], joinPoint.getArgs()[i]);
        }

        if (loggerBean.level() == LogLevel.INFO) {
            log.info("[{} : {}]------------------------INICIO------------------------", joinPoint.getTarget().getClass().getCanonicalName(),
                    joinPoint.getSignature().getName());
        } else if (loggerBean.level() == LogLevel.DEBUG) {
            log.debug("[{} : {}]------------------------INICIO------------------------", joinPoint.getTarget().getClass().getCanonicalName(),
                    joinPoint.getSignature().getName());
            log.debug("[{}:{}] Parametros enviados: {}", joinPoint.getTarget().getClass().getCanonicalName(),
                    joinPoint.getSignature().getName(), args);
        }

        Object proceed = joinPoint.proceed();

        if (loggerBean.level() == LogLevel.INFO) {
            log.info("[{} : {}]------------------------FIM------------------------\"", joinPoint.getTarget().getClass().getCanonicalName(),
                    joinPoint.getSignature().getName());
        } else if (loggerBean.level() == LogLevel.DEBUG) {
            log.debug("[{} : {}]------------------------FIM------------------------", joinPoint.getTarget().getClass().getCanonicalName(),
                    joinPoint.getSignature().getName());
        }

        return proceed;
    }

    @AfterThrowing(value = "@annotation(LoggerBean)", throwing = "ex")
    public void logError(JoinPoint joinPoint, Exception ex) {
        log.error("[{} : {}][ERRO - {}]", joinPoint.getTarget().getClass().getCanonicalName(),
                joinPoint.getSignature().getName(), ex.getMessage());
    }
}
