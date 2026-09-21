# Music Playlist Manager - Laboratoire 3

## Equipe

| Nom | Contribution principale |
| --- | --- |
| Emile Valade | Modele, DAO, services, base de donnees |
| Jean-Simon Cyr | Interface JavaFX, controleurs, filtres, tri, pagination, playlists |

## Sujet

Sujet 3 - Spotify Playlist Manager

Depot GitHub public : https://github.com/Kkiriya/Music-Playlist-Manager

## Prerequis

- Java 17 ou plus
- Maven
- PostgreSQL 14 ou plus
- Base de donnees PostgreSQL nommee `music_playlist_manager`

## Configuration de la base de donnees

1. Creer la base :

```sql
CREATE DATABASE music_playlist_manager;
```

2. Executer le schema :

```text
src/main/resources/com/maisonneuve/music_playlist_manager/schema.sql
```

3. Executer les donnees :

```text
src/main/resources/com/maisonneuve/music_playlist_manager/data.sql
```

4. Creer le fichier local suivant :

```text
src/main/resources/database.properties
```

Exemple :

```properties
DB_URL=jdbc:postgresql://localhost:5432/music_playlist_manager
DB_USER=postgres
DB_PASSWORD=your-password-here
```

Le vrai fichier `database.properties` ne doit pas etre pousse sur GitHub. Un exemple est fourni dans :

```text
src/main/resources/database.properties.example
```

## Lancement

```bash
mvn clean compile
mvn javafx:run
```

## Fonctionnalites Lab 3

- Chargement des chansons depuis PostgreSQL.
- Scripts SQL rejouables pour creer et remplir la base.
- Pattern DAO utilise pour isoler les requetes SQL.
- Requetes SQL avec `PreparedStatement`.
- Interface JavaFX conservee du Lab 2 : table, details, filtres, recherche, pagination, tris et benchmark.
- CRUD minimal sur les playlists depuis l'interface :
  - creer une playlist;
  - modifier son nom;
  - supprimer une playlist;
  - ajouter une chanson selectionnee a une playlist;
  - retirer une chanson selectionnee d'une playlist;
  - afficher les chansons d'une playlist.
- Gestion de base des erreurs avec des `Alert` JavaFX.

## Fonctionnalites non implementees

- Authentification.
- Favoris persistants par utilisateur.
- Statistiques SQL et graphiques.
- Chargement asynchrone.
- API externe.

## Notes

Le CRUD principal actuellement expose dans l'interface est celui des playlists. Les chansons sont chargees depuis PostgreSQL et restent disponibles pour les filtres, les tris, la pagination et l'ajout dans les playlists.

