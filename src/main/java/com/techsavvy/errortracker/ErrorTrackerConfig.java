package com.techsavvy.errortracker;


import lombok.Getter;

public class ErrorTrackerConfig {
    @Getter
    private static String backendUrl;
    @Getter
    private static String serviceName;

    public static void init(String backendUrl,String serviceName){
        ErrorTrackerConfig.backendUrl=backendUrl;
        ErrorTrackerConfig.serviceName=serviceName;
    }

}
