module com.maisonneuve.music_playlist_manager {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires java.sql;


    opens com.maisonneuve.music_playlist_manager to javafx.fxml;
    opens com.maisonneuve.music_playlist_manager.controller to javafx.fxml;
    exports com.maisonneuve.music_playlist_manager;
}
