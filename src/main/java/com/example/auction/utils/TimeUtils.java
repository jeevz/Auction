package com.example.auction.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeUtils {

    //TODO: Add function to convert LocalDateTime to UTC Zone.

    public static LocalDateTime utcNow() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static Duration durationBetween(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end);
    }

    public static Long secondsBetween(LocalDateTime start, LocalDateTime end) {
        return durationBetween(start, end).getSeconds();
    }

    public static Long secondsFromNow(LocalDateTime start) {
        return secondsBetween(utcNow(), start);
    }

    public static Duration durationFromNow(LocalDateTime start) {
        return durationBetween(utcNow(), start);
    }
}
