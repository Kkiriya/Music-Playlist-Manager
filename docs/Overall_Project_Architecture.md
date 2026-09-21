# Overall project architecture

## Architecture Diagram
represents the overall flow of the program
```mermaid
---
config:
    theme: redux-color
---
flowchart TD
    %% UI connection
    jFx --> Catalog
    jFx --> Services

    %% Catalog connection
    CS --> CSR
    CS --> SSC
    CSR --> S

    Services --> DAO --> PostgreSQL

    jFx[JavaFx UI]
    
    subgraph Catalog
        CS[CatalogService]
            CSR[CsvSongRepository]
                S[songs.csv]
            SSC[SongSearchCriteria]
    end

    subgraph Services
        SS[SongService]
        PS[PlaylistService]

    end

    subgraph DAO
        SDAO[SongDAO]
        PDAO[PlaylistDAO]
    end

    subgraph PostgreSQL
        SONG
        PLAYLIST
        PLAYLIST_SONG
    end

```

## Entity Relation Diagram 
represents PostgreSQL DB
```mermaid
---
config:
    theme: redux-color
---
erDiagram
    PLAYLIST ||--o{ PLAYLIST_SONG : contains
    SONG ||--o{ PLAYLIST_SONG: references

    SONG {
        VARCHAR song_id PK
        VARCHAR title
        VARCHAR artist
        VARCHAR album
        INTEGER release_year
        VARCHAR genre
        INTEGER duration_seconds
        INTEGER listen_count

        DATE created_at
        DATE updated_at
    }

    PLAYLIST {
        VARCHAR playlist_id PK
        VARCHAR name

        DATE created_at
        DATE updated_at
    }

    PLAYLIST_SONG {
        VARCHAR playlist_id PK, FK
        VARCHAR song_id PK, FK
        INTEGER position

        DATE created_at
        DATE updated_at
    }
```
## Class Diagram
represent the structure of the backend itself
```mermaid
---
config:
    theme: redux-color
---
classDiagram
    direction LR

    Library "1" o-- "0..*" Playlist: contains
    Library "1" o-- "0..*" Song: contains

    Playlist "0..*" --> "0..*" Song: references

    SongService --> SongDAO : uses
    PlaylistService --> PlaylistDAO : uses

    SongService ..> Song : manages
    PlaylistService ..> Playlist: manages

    Song --> Genre: uses

    CatalogService --> SongRepository : uses
    CatalogService --> SongSearchCriteria : uses

    namespace Models {
        class Library {
            -List~Song~ songs
            -List~Playlist~ playlists

            +getSongs() List~Song~
            +addSong(Song) void
            +removeSong(String) void
            +getPlaylist() List~Playlist~
            +addPlaylist(Playlist) void
            +removePlaylist(String) void
        }

        class Song {
            -String songId
            -String title
            -String artist
            -String album
            -int releaseYear
            -Genre genre
            -int duration
            -int listenCount

            -LocalDate createdAt
            -LocalDate addedAt

            +Song(...)
            +getSongId() String
            +getTitle() String
            +getArtist() String
            +getAlbum() String
            +getReleaseYear() int
            +getGenre() Genre
            +getDuration() int
            +getListenCount() int
            +incrementListenCount() void
        }

        class Playlist {
            -String playlistId
            -String name
            -List~String~ songIds
            -int runtime

            -LocalDate createdAt
            -LocalDate addedAt

            +Playlist(...)
            +addSong(String) void
            +removeSong(String) void
            +reorderSong(String, int) void
            +clear() void
        }

        class Genre {
            <<Enum>>
            POP
            ROCK
            HIP_HOP
            JAZZ
            CLASSICAL
            ELECTRONIC
            METAL
            COUNTRY
            RNB
            REGGAE
        }
    }

    namespace Services {
        class PlaylistService {
            +createPlaylist(Playlist playlist) void
            +getPlaylist(String playlistId) Playlist
            +getAllPlaylists() List~Playlist~
            +updatePlaylist(String playlistId, Playlist playlist) void
            +deletePlaylist(String playlistId, Playlist playlist) void
            +addSong(String playlistId, String songId) void
            +removeSong(String playlistId, String songId) void
            +reorderSong(String playlistId, String songId, int position) void

            +getRuntime(String playlistId)
            +updateRuntime(String playlistId)
        }

        class SongService {
            +createSong(Song song) void
            +getSong(String songId) Song
            +getAllSongs() List~Song~
            +updateSong(Song songId) void
            +deleteSong(String songId) void
            +incrementListenCount(String songId) void
        }
    }

    namespace DAO {
        class PlaylistDAO {
            +create(Playlist playlist) void
            +findById(String playlistId) Playlist
            +findAll() List~Playlist~
            +update(String playlistId)
            + delete(String playlistId) void
        }

        class PlaylistSongDAO {
            +findSongs(String playlistId)
            +addSong(String playlistId, String songId)
            +removeSong(String playlistId, String songId)
            +updatePosition(String playlistId, String songId, int position)
        }

        class SongDAO {
            +create(Song song) void
            +findById(String songId) Song
            +findAll() List~Song~
            +update(Song song) void
            +delete(String songId) void
        }
    }

    namespace Catalog { 
        class CatalogService {
                +search(SongSearchCriteria songSearchCriteria) List~Song~
                +getSong(String songId) Song
            }
            class SongRepository {
                -String csvPath

                +findById(String songId) song
                +findAll() List<Song>
                +search(SongSearchCriteria songSearchCriteria) List<Song>
            }

            class SongSearchCriteria {
                -String name
                -String artist
                -String songId
                -Genre genre
                -int releaseYear

                +matches(Song song) boolean
            }
    }
    note for Catalog "ALlows the frontend to fetch all songs in the csv before adding them to the DB aka the library <br>CSV could be replaced by an actual API and it would work without having to change much"
```
