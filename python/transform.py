import pandas as pd



# imports the dataset and only keeps columns we intend to use
df = pd.read_csv(
    "./data/spotify_data clean.csv",
                 usecols=[
                         "track_id",
                         "track_name",
                         "track_number",
                         "artist_name",
                         "artist_genres",
                         "album_id",
                         "album_name",
                         "album_release_date",
                         "album_total_tracks",
                         "album_type",
                         "track_duration_min",
                     ]
        )

# removes all row with empty/missing columns
df.dropna(inplace=True)

# renames the columns to use our nomenclature
df = df.rename(columns={
    "track_id": "songId",
    "track_name": "title",
    "artist_name": "artist",
    "artist_genres": "genre",
    "album_release_date": "releaseDate",
    "track_duration_min": "duration",
})

# Add the column listenCount and sets it to 0 for all entries
df["listenCount"] = 0

# Keep only 500 songs while preserving the genre distribution

# Max number of songs to keep
MAX_SONGS = 500;

# calculate how many songs to take from each genre
genre_counts = df['genre'].value_counts()
genre_percentages = genre_counts / len(df)

# allocate 500 songs proportionally
samples_per_genre = (genre_percentages * MAX_SONGS).round().astype(int)

# makes sure we dont request more songs than a genre actually has
samples_per_genre = samples_per_genre.clip(upper=genre_counts)

#sample from each genre
df = pd.concat([
    group.sample(
        n=samples_per_genre[genre],
        random_state=42
    )
    for genre, group in df.groupby("genre")
])

# Shuffle the final dataset
df = df.sample(frac=1, random_state=42).reset_index(drop=True)

# Makes sure we have exactly 500 songs
df = df.head(MAX_SONGS)

# Saves the transformed dataset to csv
df.to_csv("../src/main/resources/com/maisonneuve/music_playlist_manager/data/songs.csv", index=False)

print(f"Saved {len(df)} songs.")
