package com.maisonneuve.music_playlist_manager.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connexion {
    private static final Properties properties = new Properties();

    // static initialization block
    // automatically runs when the class is first loaded by the JVM
    static {
        try (InputStream input = Connexion.class
                .getClassLoader()
                .getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new RuntimeException(
                        "database.properties not found"
                );
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load database.properties", e);
        }
    }

    private static final String URL = properties.getProperty("DB_URL");
    private static final String USER = properties.getProperty("DB_USER");
    private static final String PASS = properties.getProperty("DB_PASSWORD");

    private Connexion() {

    }

    public static Connection open() throws SQLException {
        return DriverManager.getConnection(URL,USER, PASS);
    }
}
