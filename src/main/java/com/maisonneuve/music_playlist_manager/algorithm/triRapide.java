package com.maisonneuve.music_playlist_manager.algorithm;

import com.maisonneuve.music_playlist_manager.model.Song;

import java.util.Comparator;
import java.util.List;

public class triRapide implements Algorithm {
    @Override
    public String nom() {
        return "Quick sort";
    }

    @Override
    public String complexiteTheorique() {
        return "O(n log n) average";
    }

    @Override
    public void trier(List<Song> songs, Comparator<Song> comparator) {
        quickSort(songs, 0, songs.size() - 1, comparator);
    }

    private void quickSort(List<Song> songs, int start, int end, Comparator<Song> comparator) {
        if (start >= end) {
            return;
        }

        // Put the pivot in its final position.
        int pivotIndex = partition(songs, start, end, comparator);
        quickSort(songs, start, pivotIndex - 1, comparator);
        quickSort(songs, pivotIndex + 1, end, comparator);
    }

    private int partition(List<Song> songs, int start, int end, Comparator<Song> comparator) {
        Song pivot = songs.get(end);
        int smallerIndex = start - 1;

        // Move smaller values before the pivot.
        for (int i = start; i < end; i++) {
            if (comparator.compare(songs.get(i), pivot) <= 0) {
                smallerIndex++;
                swap(songs, smallerIndex, i);
            }
        }

        swap(songs, smallerIndex + 1, end);
        return smallerIndex + 1;
    }

    private void swap(List<Song> songs, int firstIndex, int secondIndex) {
        Song temp = songs.get(firstIndex);
        songs.set(firstIndex, songs.get(secondIndex));
        songs.set(secondIndex, temp);
    }
}
