package com.maisonneuve.music_playlist_manager.algorithm;

import com.maisonneuve.music_playlist_manager.model.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class triMerge implements Algorithm {
    @Override
    public String nom() {
        return "Merge sort";
    }

    @Override
    public String complexiteTheorique() {
        return "O(n log n)";
    }

    @Override
    public void trier(List<Song> songs, Comparator<Song> comparator) {
        if (songs.size() <= 1) {
            return;
        }

        List<Song> sortedSongs = mergeSort(new ArrayList<>(songs), comparator);
        for (int i = 0; i < sortedSongs.size(); i++) {
            songs.set(i, sortedSongs.get(i));
        }
    }

    private List<Song> mergeSort(List<Song> songs, Comparator<Song> comparator) {
        if (songs.size() <= 1) {
            return songs;
        }

        // Split the list in two, sort both sides, then merge them.
        int middle = songs.size() / 2;
        List<Song> left = mergeSort(new ArrayList<>(songs.subList(0, middle)), comparator);
        List<Song> right = mergeSort(new ArrayList<>(songs.subList(middle, songs.size())), comparator);

        return merge(left, right, comparator);
    }

    private List<Song> merge(List<Song> left, List<Song> right, Comparator<Song> comparator) {
        List<Song> result = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        // Add the smallest next song from either side.
        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (comparator.compare(left.get(leftIndex), right.get(rightIndex)) <= 0) {
                result.add(left.get(leftIndex));
                leftIndex++;
            } else {
                result.add(right.get(rightIndex));
                rightIndex++;
            }
        }

        // Add what is left after one side is empty.
        while (leftIndex < left.size()) {
            result.add(left.get(leftIndex));
            leftIndex++;
        }

        while (rightIndex < right.size()) {
            result.add(right.get(rightIndex));
            rightIndex++;
        }

        return result;
    }
}
