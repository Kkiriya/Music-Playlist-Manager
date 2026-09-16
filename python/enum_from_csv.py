import pandas as pd
import re

# Extracts all unique values for the genre collumn and then generates a
# java enum with the extracted values

CSV_FILE = "./data/spotify_data clean.csv"
OUTPUT_FILE = "../src/main/java/com/maisonneuve/music_playlist_manager/model/Genre.java"
PACKAGE_NAME = "com.maisonneuve.music_playlist_manager.model"

# Read csv
df = pd.read_csv(CSV_FILE)

# Extract every genre from every row
genres = (
    df["artist_genres"]
        .dropna()
        .astype(str)
        .str.split(",")
        .explode()
        .astype(str)
        .str.strip()
)

# Remove empty values and duplicates
genres = sorted(
    genre
    for genre in genres.unique()
    if genre
)

def to_enum_constant(genre: str) -> str:
    # Convert a genre name into a valid Java enum constant
    # Example: 'Hip Hop' -> 'HIP_HOP'
    value = genre.upper()

    # replace '&' with AND
    value = value.replace("&", " AND ")

    # replace anything that isnt a letter or number with _
    value = re.sub(r"[^A-Z0-9]+", "_", value)

    # remove duplicate underscore
    value = re.sub(r"_+", "_", value)

    # remove underscore at the beginning/end
    value = value.strip("_")

    # Java identifiers cant start with a number
    if value and value[0].isdigit():
        value = "_" + value

    return value

def escape_java_string(value: str) -> str:
    # Escape a string so it can safely be used in java source code.
    return value.replace("\\", "\\\\").replace('"', '\\"')

# generate enum values
enum_values = [
    f'    {to_enum_constant(genre)}("{escape_java_string(genre)}")'
    for genre in genres
]

java_code = f"""package {PACKAGE_NAME};

public enum Genre {{
{",\n".join(enum_values)};

    private final String displayName;

    Genre(String displayName) {{
        this.displayName = displayName;
    }}

    @Override
    public String toString() {{
        return displayName;
    }}
}}
"""


# Write Java file
with open(OUTPUT_FILE, "w", encoding="utf-8") as f:
    f.write(java_code)

print(f"Found {len(genres)} unqiques genres.")
print(f"Generated {OUTPUT_FILE}")
