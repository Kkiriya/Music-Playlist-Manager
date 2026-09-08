module com.maisonneuve.music_playlist_manager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.maisonneuve.music_playlist_manager to javafx.fxml;
    exports com.maisonneuve.music_playlist_manager;
}
