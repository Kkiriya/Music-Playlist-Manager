package com.maisonneuve.music_playlist_manager.algorithm;

import com.maisonneuve.music_playlist_manager.model.Song;

import java.util.Comparator;
import java.util.List;

public class triBulle implements Algorithm {
    @Override
    public String nom() {
        return "Bubble sort";
    }

    @Override
    public String complexiteTheorique() {
        return "O(n^2)";
    }

    @Override
    public void trier(List<Song> songs, Comparator<Song> comparator) {
        for (int i = 0; i < songs.size() - 1; i++) {
            // The biggest value moves to the end after each pass.
            for (int j = 0; j < songs.size() - 1 - i; j++) {
                if (comparator.compare(songs.get(j), songs.get(j + 1)) > 0) {
                    Song temp = songs.get(j);
                    songs.set(j, songs.get(j + 1));
                    songs.set(j + 1, temp);
                }
            }
        }
    }
}
