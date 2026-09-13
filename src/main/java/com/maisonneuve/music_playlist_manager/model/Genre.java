package com.maisonneuve.music_playlist_manager.model;

public enum Genre {
    POP("Pop"),
    ROCK("Rock"),
    HIP_HOP("Hip Hop"),
    JAZZ("Jazz"),
    CLASSICAL("Classique"),
    ELECTRONIC("Electronique");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
