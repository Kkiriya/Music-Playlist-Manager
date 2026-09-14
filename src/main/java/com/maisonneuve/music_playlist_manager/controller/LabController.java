package com.maisonneuve.music_playlist_manager.controller;

import com.maisonneuve.music_playlist_manager.algorithm.Algorithm;
import com.maisonneuve.music_playlist_manager.algorithm.triBulle;
import com.maisonneuve.music_playlist_manager.algorithm.triInsertion;
import com.maisonneuve.music_playlist_manager.algorithm.triMerge;
import com.maisonneuve.music_playlist_manager.algorithm.triRapide;
import com.maisonneuve.music_playlist_manager.algorithm.triSelection;
import com.maisonneuve.music_playlist_manager.model.Song;
import com.maisonneuve.music_playlist_manager.util.CsvReader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabController {
    private static final int[] TAILLES = {100, 500, 1_000, 5_000, 10_000, 50_000, 100_000};
    private static final int[] TAILLES_LENTES = {100, 500, 1_000, 2_000, 5_000, 10_000};

    @FXML
    private CheckBox chkLineaire;
    @FXML
    private CheckBox chkDico;
    @FXML
    private CheckBox chkDouble;
    @FXML
    private CheckBox chkHash;
    @FXML
    private CheckBox chkTriBulle;
    @FXML
    private CheckBox chkTriInsertion;
    @FXML
    private CheckBox chkTriSelection;
    @FXML
    private CheckBox chkTriMerge;
    @FXML
    private CheckBox chkTriRapide;
    @FXML
    private Spinner<Integer> spinnerRepetition;
    @FXML
    private Button btnLancer;
    @FXML
    private Button btnReset;
    @FXML
    private NumberAxis axeX;
    @FXML
    private NumberAxis axeY;
    @FXML
    private LineChart<Number, Number> lineChart;

    private final List<Song> sourceSongs = new CsvReader().readSongs();

    @FXML
    public void initialize() {
        spinnerRepetition.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 10));
        lineChart.setAnimated(false);
        axeX.setForceZeroInRange(false);
        axeY.setForceZeroInRange(true);

        btnLancer.setOnAction(event -> lancerBenchmark());
        btnReset.setOnAction(event -> reset());
    }

    private void lancerBenchmark() {
        List<BenchmarkAlgo> algos = collecterAlgorithmes();

        if (algos.isEmpty()) {
            afficherAlert("Veuillez selectionner au moins un algorithme a tester.");
            return;
        }

        lineChart.getData().clear();
        int repetitions = spinnerRepetition.getValue();

        new Thread(() -> {
            for (BenchmarkAlgo algo : algos) {
                XYChart.Series<Number, Number> serie = new XYChart.Series<>();
                serie.setName(algo.nom());

                int[] tailles = algo.lent() ? TAILLES_LENTES : TAILLES;
                for (int n : tailles) {
                    long tempsNs = mesurer(algo, n, repetitions);
                    Platform.runLater(() -> serie.getData().add(new XYChart.Data<>(n, tempsNs)));
                }

                Platform.runLater(() -> lineChart.getData().add(serie));
            }
        }).start();
    }

    private List<BenchmarkAlgo> collecterAlgorithmes() {
        List<BenchmarkAlgo> algos = new ArrayList<>();

        if (chkDico.isSelected()) {
            algos.add(new BenchmarkAlgo("Recherche Dichotomique", false, this::rechercheDichotomique));
        }
        if (chkLineaire.isSelected()) {
            algos.add(new BenchmarkAlgo("Recherche Lineaire", false, this::rechercheLineaire));
        }
        if (chkDouble.isSelected()) {
            algos.add(new BenchmarkAlgo("Double Boucle", true, this::doubleBoucle));
        }
        if (chkHash.isSelected()) {
            algos.add(new BenchmarkAlgo("HashMap.get()", false, this::hashMapGet));
        }
        if (chkTriBulle.isSelected()) {
            algos.add(sortBenchmark(new triBulle(), true));
        }
        if (chkTriInsertion.isSelected()) {
            algos.add(sortBenchmark(new triInsertion(), true));
        }
        if (chkTriSelection.isSelected()) {
            algos.add(sortBenchmark(new triSelection(), true));
        }
        if (chkTriRapide.isSelected()) {
            algos.add(sortBenchmark(new triRapide(), false));
        }
        if (chkTriMerge.isSelected()) {
            algos.add(sortBenchmark(new triMerge(), false));
        }

        return algos;
    }

    private BenchmarkAlgo sortBenchmark(Algorithm algorithm, boolean lent) {
        return new BenchmarkAlgo(algorithm.nom(), lent, n -> {
            List<Song> songs = creerListeChansons(n);
            Comparator<Song> comparator = Comparator.comparing(Song::getTitle, String.CASE_INSENSITIVE_ORDER);
            algorithm.trier(songs, comparator);
        });
    }

    private long mesurer(BenchmarkAlgo algo, int n, int repetitions) {
        long total = 0;

        for (int i = 0; i < repetitions; i++) {
            long debut = System.nanoTime();
            algo.executer(n);
            total += System.nanoTime() - debut;
        }

        return total / repetitions;
    }

    private void rechercheLineaire(int n) {
        int[] donnees = creerTableau(n);
        int cible = n - 1;

        for (int valeur : donnees) {
            if (valeur == cible) {
                return;
            }
        }
    }

    private void rechercheDichotomique(int n) {
        int[] donnees = creerTableau(n);
        int cible = n - 1;
        int gauche = 0;
        int droite = donnees.length - 1;

        while (gauche <= droite) {
            int milieu = (gauche + droite) / 2;
            if (donnees[milieu] == cible) {
                return;
            }
            if (donnees[milieu] < cible) {
                gauche = milieu + 1;
            } else {
                droite = milieu - 1;
            }
        }
    }

    private void doubleBoucle(int n) {
        long total = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                total += i + j;
            }
        }

        if (total < 0) {
            throw new IllegalStateException();
        }
    }

    private void hashMapGet(int n) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(i, i);
        }

        map.get(n - 1);
    }

    private List<Song> creerListeChansons(int n) {
        List<Song> songs = new ArrayList<>();

        if (sourceSongs.isEmpty()) {
            return songs;
        }

        for (int i = 0; i < n; i++) {
            songs.add(sourceSongs.get(i % sourceSongs.size()));
        }

        return songs;
    }

    private int[] creerTableau(int n) {
        int[] donnees = new int[n];
        for (int i = 0; i < n; i++) {
            donnees[i] = i;
        }
        return donnees;
    }

    private void reset() {
        lineChart.getData().clear();
    }

    private void afficherAlert(String message) {
        new Alert(Alert.AlertType.WARNING, message).showAndWait();
    }

    private record BenchmarkAlgo(String nom, boolean lent, BenchmarkAction action) {
        void executer(int n) {
            action.executer(n);
        }
    }

    private interface BenchmarkAction {
        void executer(int n);
    }
}
