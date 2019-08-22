# Song-Database-Application

Final Report

Evan Reilly, Luke Lamberg


I.	Introduction

The application allows users to login or create a username and password for the next time they visit. After logging in or creating a new account, users are directed to the main part of the application where they are allowed to search for songs, albums, artists, and users.  When songs are displayed via song search, artist search, or album search, users may add any of the displayed songs to playlists they have created.  When usernames of others using the application are displayed via user search, they may be ‘followed’ in order to see what playlists they have created as well as what songs are within the playlists they’ve created.  They can even add these user’s songs to their own playlist of choice.

II.	 Implementation
 
  2.1	System Architecture

The architecture of the application consists of three main parts.  These include the front-end, back-end, and version control.  First, the language used for the front-end is Java.  However, Java alone does not allow for the original goals of the application to be reached efficiently.  Therefore, it is necessary to use libraries.  The Swing library was chosen to implement some basic visual features of the application.  However, the WindowBuilder library was additionally used to create a more pleasantly looking and functioning Graphical User Interface.  Second, the back-end is MySQL.  It was the database management system of choice because it provides an interface that is not only easy to use, but is also quicker than other free database management systems on the market.  Finally, GitHub was chosen for version control.  Not only can the versions of the newly updated application be kept track of, but the application can also be worked on by multiple team members simultaneously.  This allows for the efficient use of team member’s time during development.

2.2   Dataset Description

The “Million Song Dataset” the application uses is actually a subset of the original Million Song Dataset that consists of 10,000 different songs with a wide variety of attributes.  Many of these attributes are complex data points that are primarily concerned with providing valuable information for people interested in using the dataset for sophisticated machine learning algorithms.  However, these data points don’t have much purpose when it comes to reaching the original goals set out for the project. Within this dataset, however, there are three primary keys that may be extracted for use within the application.  These include song.id, artist.id, and release.id (album.id).  By finding each unique artist.id, it is determined that the dataset contains 3,887 different artists.  Similarly, there are 8,115 different albums within the dataset when counting the unique instances of release.id.  Besides identification attributes, there exists other important attributes that may be valuable within the application.  These include title, artist.name, release.name (album.name), and artist_mbtags among others.  

2.3   ER diagram

![millionsongerdiagram](https://user-images.githubusercontent.com/39227780/63539016-b92c9000-c4de-11e9-9181-b4da76b69fa7.png)

2.4   Relational model 

Song(SongID(primary key), title, duration, familiarity, key, keyConfidence, latitude, longitude, loudness, barsStart, similar, songHottness, tempo, timeSignature, timeSignatureConfidence, ArtistID, AlbumID)
	
Artist(ArtistID(primary key), artistName, artistHottness, artistMBTags)

Album(AlbumID(primary key), albumName, releaseYear, ArtistID)

User(UserID(primary key), username, password)
	
Playlist(PlaylistID(primary key), playlistName, UserID)
	
Include(PlaylistID, SongID)
SongID & PlaylistID are the primary key (composite key)

Follow(UserID, UserID)
UserID & UserID are the primary key (composite key)


2.5   Implementation

The prototype allows users to login and/or create an account.  After logging in, there are certain tasks the user may accomplish by using three lists, all containing their own functionality.  These include the “Playlist” list, the main list, and the “Followed” list. 

 The playlist list is an area of the application that, when right-clicked, will display a popup menu with three options (if applicable).  These include “Create New Playlist”, “Rename”, and “Delete”.  When users click “Create New Playlist”, they are prompted to enter a playlist name.  This name cannot be empty and cannot be identical to a playlist already created by the user.  When users click “Rename”, they may rename the selected playlist as long as the name is not empty and is not an identical name, just like when creating a playlist.  Finally, users may click “Delete” to remove the selected playlist from the user’s playlist list. 

The application is able to display queries made by the user when searching based on specific criteria of a song or user by using the main list of the application.  When the song radio button is clicked, users can explicitly search and display songs within the dataset.  Users are then able to add these displayed songs to playlists that they create by double-clicking them while a designated playlist is selected.  However, besides searching for songs explicitly, users may also add songs to playlists by searching for songs via artist or album by using their designated radio buttons.  When the artist search is executed, a list of artists are displayed within the main list of the application that match the search criteria.  Once double-clicked, songs specific to the artist are then displayed in the same main list.  From this point, users may add the displayed songs to their designated playlist of choice just like with the explicit song search.  Furthermore, the album search works in an identical way to the artist search.  Songs specific to the double-clicked album are displayed instead.  Besides displaying queries, the main list also allows users to delete songs from their playlists.  The main list displays a playlist’s song contents when the playlist is double-clicked in order to allow for deletion of songs.  Users may then right click one of the displayed songs and proceed by clicking the “Delete” option from the popup menu to remove it from the selected playlist.

Besides displaying songs within the main list, the application also allows for the user to search for other existing users and display them within the main list as long as the user radio button is clicked before the search.  However, unlike songs, artists, or albums, when any of the displayed users are double-clicked, they will be “followed” by the current user of the application and added to their “followed” user list.  Now, if this same user double-clicks any of their “followed” users, that particular user’s playlists will be displayed within the main list.  From here, they may double-click any of the displayed playlists to reveal the songs within them.  Now, they may simply double-click the displayed songs to add to any existing playlist the user currently has, just like when adding songs via song, artist, and album search.

Next is the functionality of the “Followed” list.  It was already mentioned that users may be searched and added to this list and used for displaying a specific user’s playlists.  However, besides adding users to this list, users may also be deleted from it by clicking “Delete” from the popup menu after right-clicking a given username.
