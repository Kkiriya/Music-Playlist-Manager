package com.maisonneuve.music_playlist_manager.algorithm;

import com.maisonneuve.music_playlist_manager.model.Song;

import java.util.Comparator;
import java.util.List;

public class triSelection implements Algorithm {
    @Override
    public String nom() {
        return "Selection sort";
    }

    @Override
    public String complexiteTheorique() {
        return "O(n^2)";
    }

    @Override
    public void trier(List<Song> songs, Comparator<Song> comparator) {
        for (int i = 0; i < songs.size() - 1; i++) {
            int minIndex = i;

            // Find the smallest song in the unsorted part.
            for (int j = i + 1; j < songs.size(); j++) {
                if (comparator.compare(songs.get(j), songs.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }

            // Put it at the current position.
            Song temp = songs.get(minIndex);
            songs.set(minIndex, songs.get(i));
            songs.set(i, temp);
        }
    }
}
