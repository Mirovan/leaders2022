package ru.bigint.webapp.utils;

import org.slf4j.Logger;

public class LoggerUtil {
    public static void writeError(Logger LOGGER, String error) {
        LOGGER.error("[Error]: " + error);
        System.err.println("[Error]: " + error);
    }

    public static void writeInfo(Logger LOGGER, String data) {
        LOGGER.info("[Info]: " + data);
        System.out.println("[Info]: " + data);
    }
}
