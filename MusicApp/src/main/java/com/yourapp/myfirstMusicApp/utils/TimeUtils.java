package com.yourapp.myfirstMusicApp.utils;

import java.time.Duration;

public class TimeUtils {
    public static String formatDuration(long durationInSeconds) {
        Duration duration = Duration.ofSeconds(durationInSeconds);
        long minutes = duration.toMinutes();
        long seconds = duration.getSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
