module com.maisonneuve.musicwebapptp2algo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.maisonneuve.musicwebapptp2algo to javafx.fxml;
    exports com.maisonneuve.musicwebapptp2algo;
}