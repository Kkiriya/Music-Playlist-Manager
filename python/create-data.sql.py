import pandas as pd


# imports the dataset and only keeps columns we intend to use
df = pd.read_csv(
    "./data/spotify_data clean.csv",
                 usecols=[
                         "track_id",
                         "track_name",
                         "artist_name",
                         "artist_genres",
                         "album_name",
                         "album_release_date",
                         "track_duration_min",
                     ]
        )

# removes all row with missing values
df.dropna(inplace=True)

# renames the columns to use our nomenclature
df = df.rename(columns={
    "track_id": "song_id",
    "track_name": "title",
    "artist_name": "artist",
    "artist_genres": "genre",
    "album_name": "album",
    "album_release_date": "release_date",
    "track_duration_min": "duration_seconds",
})

# convert duration from minutes to seconds
df["duration_seconds"] = (df["duration_seconds"] * 60).round().astype(int)

# Extract the year from the release date
df["release_year"] = (
    df["release_date"]
    .astype(str)
    .str[:4]
)

# Convert the extracted year to a number
df["release_year"] = pd.to_numeric(
    df["release_year"],
    errors="coerce"
)

# Remove rows where the year could not be determined
df.dropna(subset=["release_year"], inplace=True)

df["release_year"] = df["release_year"].astype(int)

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

# Generates the SQL

def sql_escape(value):
    # Escape a Python string so it can safely be used as a PostgreSQL string literal
    if pd.isna(value):
        return "NULL"

    value = str(value)

    # PostgreSQL escapes singles quotes by doubling them
    value = value.replace("'", "''")

    return f"'{value}'"

def sql_date(value):
    # converts a date/year into a postgreSQL DATE
    if pd.isna(value):
        return "NULL"

    return f"'{int(value)}-01-01'"


sql_lines = []

sql_lines.append("-- ================================================")
sql_lines.append("-- Music Playlist Manager")
sql_lines.append("-- Song catalog")
sql_lines.append("-- Generated automatically from the CSV")
sql_lines.append("-- ================================================")
sql_lines.append("")
sql_lines.append("INSERT INTO SONG (")
sql_lines.append("  song_id,")
sql_lines.append("  title,")
sql_lines.append("  artist,")
sql_lines.append("  album,")
sql_lines.append("  release_year,")
sql_lines.append("  genre,")
sql_lines.append("  duration_seconds")
sql_lines.append(") VALUES")

for i, (_, row) in enumerate(df.iterrows()):
    separator = "," if i < len(df) - 1 else ";"
    sql_lines.append(
        f"  ("
        f"{sql_escape(row['song_id'])}, "
        f"{sql_escape(row['title'])}, "
        f"{sql_escape(row['artist'])}, "
        f"{sql_escape(row["album"])}, "
        f"{int(row["release_year"])}, "
        f"{sql_escape(row['genre'])}, "
        f"{int(row['duration_seconds'])} "
        f"){separator}"
    )

# Write SQL file
output_path = "../src/main/resources/com/maisonneuve/music_playlist_manager/data.sql"

with open(output_path, "w", encoding="utf-8") as file:
    file.write("\n".join(sql_lines))

print(f"Saved {len(df)} songs to {output_path}")
