package com.techsavvy.errortracker;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "error_events")
public class ErrorEvent {
    @Id
    private String id;
    private String serviceName;
    private String className;
    private String methodName;
    private String exceptionClass;
    private String message;
    private String stackTrace;
    private String timeStamp;
}
