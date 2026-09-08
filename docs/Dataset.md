# Spotify Global Music Dataset (2009-2025)

Dataset comes from: https://www.kaggle.com/datasets/wardabilal/spotify-global-music-dataset-20092025

Columns used:
| Dataset Name | Processed CSV Name | Comments |
| ------------ | ------------------ | -------- |
| track_id | songId | |
| track_name | title | |
| track_number | N/A | Will be used when implementing albums |
| artist_name | artist | |
| artist_genre | genre | |
| album_id | N/A | Will be used when implementing albums |
| album_name | N/A | Will be used when implementing albums |
| album_release_date | releaseDate | |
| album_total_tracks | N/A | Will be used when implementing albums |
| album_type | N/A | Will be used when implementing albums |
| track_duration_min | duration | |
| N/A | listenCount | |

The only thing missing in this dataset is the listenCount, we will add that column to the dataset and replace it with our own count. All songs will start with a listenCount of 0

To create the Transformed dataset on your own run the following python file
`/python/transform.py`
