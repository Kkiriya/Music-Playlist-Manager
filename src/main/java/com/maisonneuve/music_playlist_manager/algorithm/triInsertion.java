package com.maisonneuve.music_playlist_manager.algorithm;

import com.maisonneuve.music_playlist_manager.model.Song;

import java.util.Comparator;
import java.util.List;

public class triInsertion implements Algorithm {
    @Override
    public String nom() {
        return "Insertion sort";
    }

    @Override
    public String complexiteTheorique() {
        return "O(n^2)";
    }

    @Override
    public void trier(List<Song> songs, Comparator<Song> comparator) {
        for (int i = 1; i < songs.size(); i++) {
            Song currentSong = songs.get(i);
            int j = i - 1;

            // Move bigger songs one position to the right.
            while (j >= 0 && comparator.compare(songs.get(j), currentSong) > 0) {
                songs.set(j + 1, songs.get(j));
                j--;
            }

            // Place the current song in the empty spot.
            songs.set(j + 1, currentSong);
        }
    }
}
