package com.vegana.vegana.servlet.log;

import org.slf4j.MDC;

import java.util.UUID;

public final class LogKey {

    protected static final String HEADER_LOG_KEY = "logKey";

    private LogKey() {
    }

    public static String getLogKeyName() {
        return HEADER_LOG_KEY;
    }

    public static String get() {
        return MDC.get(HEADER_LOG_KEY);
    }

    public static void put(String keyValue) {
        MDC.put(HEADER_LOG_KEY, keyValue);
    }

    public static void remove() {
        MDC.remove(HEADER_LOG_KEY);
    }

    public static String createLogKey() {
        return UUID.randomUUID().toString();
    }
}
