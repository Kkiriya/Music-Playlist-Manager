-- ==================================
-- SONG
-- ==================================

CREATE TABLE song(
    song_id VARCHAR(36) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    album VARCHAR(255) NOT NULL,
    release_year INTEGER NOT NULL,
    genre VARCHAR(100),
    duration_seconds INTEGER NOT NULL,
    listen_count INTEGER NOT NULL DEFAULT 0,

    created_at DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at DATE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ==================================
-- LIBRARY
-- ==================================

CREATE TABLE library(
    library_id VARCHAR(36) PRIMARY KEY
);

-- ==================================
-- PLAYLIST
-- ==================================

CREATE TABLE playlist(
    playlist_id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,

    created_at DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ==================================
-- LIBRARY_SONGS
-- ==================================

CREATE TABLE library_songs(
    library_id VARCHAR(36) NOT NULL,
    song_id VARCHAR(36) NOT NULL,

    PRIMARY KEY (library_id, song_id),

    FOREIGN KEY (library_id)
        REFERENCES library(library_id)
        ON DELETE CASCADE,

    FOREIGN KEY (song_id)
        REFERENCES song(song_id)
        ON DELETE CASCADE
);

-- ==================================
-- LIBRARY_PLAYLIST
-- ==================================

CREATE TABLE library_playlist(
    library_id VARCHAR(36) NOT NULL,
    playlist_id VARCHAR(36) NOT NULL,

    PRIMARY KEY (library_id, playlist_id),

    FOREIGN KEY (library_id)
        REFERENCES library(library_id)
        ON DELETE CASCADE,

    FOREIGN KEY (playlist_id)
        REFERENCES playlist(playlist_id)
        ON DELETE CASCADE
);

-- ==================================
-- PLAYLIST_SONG
-- ==================================

CREATE TABLE playlist_song (
    playlist_id VARCHAR(36) NOT NULL,
    song_id VARCHAR(36) NOT NULL UNIQUE,
    position INTEGER NOT NULL,

    created_at DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (playlist_id, song_id),

    FOREIGN KEY (playlist_id)
        REFERENCES playlist(playlist_id)
        ON DELETE CASCADE,

    FOREIGN KEY (song_id)
        REFERENCES song(song_id)
        ON DELETE CASCADE,

    -- ensures a postion can only be occupied once in a playlist
    UNIQUE (playlist_id, position),

    CHECK (postition >= 0)
)
