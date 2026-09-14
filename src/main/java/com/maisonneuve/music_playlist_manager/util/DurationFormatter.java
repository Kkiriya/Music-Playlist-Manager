package com.maisonneuve.music_playlist_manager.util;

public class DurationFormatter {
    private DurationFormatter() {
    }

    public static String format(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
