DROP TABLE IF EXISTS playlist_song;
DROP TABLE IF EXISTS library_playlist;
DROP TABLE IF EXISTS library_songs;
DROP TABLE IF EXISTS playlist;
DROP TABLE IF EXISTS library;
DROP TABLE IF EXISTS song;

CREATE TABLE song
(
    song_id          VARCHAR(36) PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    artist           VARCHAR(255) NOT NULL,
    album            VARCHAR(255) NOT NULL,
    release_year     INTEGER      NOT NULL CHECK (release_year >= 1900),
    genre            VARCHAR(100) NOT NULL,
    duration_seconds INTEGER      NOT NULL CHECK (duration_seconds > 0),
    listen_count     INTEGER      NOT NULL DEFAULT 0 CHECK (listen_count >= 0),
    created_at       DATE         NOT NULL DEFAULT CURRENT_DATE,
    updated_at       DATE         NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE library
(
    library_id VARCHAR(36) PRIMARY KEY,
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    updated_at DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE playlist
(
    playlist_id VARCHAR(36) PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    created_at  DATE         NOT NULL DEFAULT CURRENT_DATE,
    updated_at  DATE         NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE library_songs
(
    library_id VARCHAR(36) NOT NULL,
    song_id    VARCHAR(36) NOT NULL,
    created_at DATE        NOT NULL DEFAULT CURRENT_DATE,
    updated_at DATE        NOT NULL DEFAULT CURRENT_DATE,

    PRIMARY KEY (library_id, song_id),

    FOREIGN KEY (library_id)
        REFERENCES library (library_id)
        ON DELETE CASCADE,

    FOREIGN KEY (song_id)
        REFERENCES song (song_id)
        ON DELETE CASCADE
);

CREATE TABLE library_playlist
(
    library_id  VARCHAR(36) NOT NULL,
    playlist_id VARCHAR(36) NOT NULL,
    created_at  DATE        NOT NULL DEFAULT CURRENT_DATE,
    updated_at  DATE        NOT NULL DEFAULT CURRENT_DATE,

    PRIMARY KEY (library_id, playlist_id),

    FOREIGN KEY (library_id)
        REFERENCES library (library_id)
        ON DELETE CASCADE,

    FOREIGN KEY (playlist_id)
        REFERENCES playlist (playlist_id)
        ON DELETE CASCADE
);

CREATE TABLE playlist_song
(
    playlist_id VARCHAR(36) NOT NULL,
    song_id     VARCHAR(36) NOT NULL,
    position    INTEGER     NOT NULL CHECK (position >= 0),
    created_at  DATE        NOT NULL DEFAULT CURRENT_DATE,
    updated_at  DATE        NOT NULL DEFAULT CURRENT_DATE,

    PRIMARY KEY (playlist_id, song_id),
    UNIQUE (playlist_id, position),

    FOREIGN KEY (playlist_id)
        REFERENCES playlist (playlist_id)
        ON DELETE CASCADE,

    FOREIGN KEY (song_id)
        REFERENCES song (song_id)
        ON DELETE CASCADE
);
