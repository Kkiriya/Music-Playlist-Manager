package com.maisonneuve.music_playlist_manager.algorithm;

import com.maisonneuve.music_playlist_manager.model.Song;

import java.util.Comparator;
import java.util.List;

public interface Algorithm {
    String nom();

    String complexiteTheorique();

    void trier(List<Song> songs, Comparator<Song> comparator);
}
