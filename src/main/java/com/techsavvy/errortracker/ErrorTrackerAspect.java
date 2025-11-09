package com.techsavvy.errortracker;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class ErrorTrackerAspect {

    private final WebClient webClient;
    private final String backendUrl;
    private final String serviceName;

    public ErrorTrackerAspect(
            WebClient.Builder webClientBuilder,
            @Value("${error.tracker.backend.url:http://localhost:8081/api/errors}") String backendUrl,
            @Value("${spring.application.name:MyService}") String serviceName
    ) {
        this.webClient = webClientBuilder.build();
        this.backendUrl = backendUrl;
        this.serviceName = serviceName;
    }

    @Around("@annotation(com.techsavvy.errortracker.TrackErrors)")
    public Object trackException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //Proceed Normally
            return joinPoint.proceed();
        } catch (Exception ex) {
            //Convert Stack trace to String
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String stackTrace = sw.toString();

            ErrorEvent errorEvent = ErrorEvent.
                    builder()
                    .serviceName(serviceName)
                    .className(joinPoint.getTarget().getClass().getSimpleName())
                    .methodName(joinPoint.getSignature().getName())
                    .exceptionClass(ex.getClass().getSimpleName())
                    .message(ex.getMessage())
                    .stackTrace(stackTrace)
                    .timeStamp(Instant.now().toString())
                    .build();

            log.error("Captured exception: {} in {}.{} - {}",
                    errorEvent.getExceptionClass(),
                    errorEvent.getClassName(),
                    errorEvent.getMethodName(),
                    ex.getMessage(), ex);

            // Send to backend asynchronously
            sendToBackend(errorEvent);

            // Rethrow to preserve flow
            throw ex;
        }
    }
    private void sendToBackend(ErrorEvent event) {
        webClient.post()
                .uri(backendUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(event))
                .retrieve()
                .toBodilessEntity()
                .doOnSuccess(response -> log.info("Error event sent successfully"))
                .doOnError(error -> log.error("Failed to send error event", error))
                .subscribe(); // 🔥 Non-blocking fire-and-forget
    }
}