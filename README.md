# Song-Database-Application

Final Report

Evan Reilly, Luke Lamberg


I.	Introduction

The application allows users to login or create a username and password for the next time they visit. After logging in or creating a new account, users are directed to the main part of the application where they are allowed to search for songs, albums, artists, and users.  When songs are displayed via song search, artist search, or album search, users may add any of the displayed songs to playlists they have created.  When usernames of others using the application are displayed via user search, they may be ‘followed’ in order to see what playlists they have created as well as what songs are within the playlists they’ve created.  They can even add these user’s songs to their own playlist of choice.

II.	 Implementation
 
2.1   System Architecture
	The architecture of the application consists of three main parts.  These include the front-end, back-end, and version control.  First, the language used for the front-end is Java.  However, Java alone does not allow for the original goals of the application to be reached efficiently.  Therefore, it is necessary to use libraries.  The Swing library was chosen to implement some basic visual features of the application.  However, the WindowBuilder library was additionally used to create a more pleasantly looking and functioning Graphical User Interface.  Second, the back-end is MySQL.  It was the database management system of choice because it provides an interface that is not only easy to use, but is also quicker than other free database management systems on the market.  Finally, GitHub was chosen for version control.  Not only can the versions of the newly updated application be kept track of, but the application can also be worked on by multiple team members simultaneously.  This allows for the efficient use of team member’s time during development.

2.2   Dataset Description
	The “Million Song Dataset” the application uses is actually a subset of the original Million Song Dataset that consists of 10,000 different songs with a wide variety of attributes.  Many of these attributes are complex data points that are primarily concerned with providing valuable information for people interested in using the dataset for sophisticated machine learning algorithms.  However, these data points don’t have much purpose when it comes to reaching the original goals set out for the project. Within this dataset, however, there are three primary keys that may be extracted for use within the application.  These include song.id, artist.id, and release.id (album.id).  By finding each unique artist.id, it is determined that the dataset contains 3,887 different artists.  Similarly, there are 8,115 different albums within the dataset when counting the unique instances of release.id.  Besides identification attributes, there exists other important attributes that may be valuable within the application.  These include title, artist.name, release.name (album.name), and artist_mbtags among others.  

2.3   ER diagram

![millionsongerdiagram](https://user-images.githubusercontent.com/39227780/63539016-b92c9000-c4de-11e9-9181-b4da76b69fa7.png)

2.4   Relational model 
Song(SongID, title, duration, familiarity, key, keyConfidence, latitude, longitude, loudness, barsStart, similar, songHottness, tempo, timeSignature, timeSignatureConfidence, ArtistID, AlbumID)
	SongID is primary key
Artist(ArtistID, artistName, artistHottness, artistMBTags)
	ArtistID is primary key
Album(AlbumID, albumName, releaseYear, ArtistID)
	AlbumID is primary key
User(UserID, username, password)
	UserID is primary key
Playlist(PlaylistID, playlistName, UserID)
	PlaylistID is primary key
	username is also a key
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


2.6   Evaluation
	The main test environment is done by creating different use cases and running through them to ensure the program is doing what it is intended to at each step. Moreover, each functionality added to a given component of the GUI is given various case scenarios of which to be tested.  When significant modifications are made to these components, it is important to run the application through similar tests afterwards to ensure the given section of the program remains functioning properly.  This makes it easier to isolate problems within the code because if we test for program correctness often, it is understood that it is not from modification of code from a long time ago that a problems arise, rather from a recent change.

When coding with the front-end, it is important to frequently include comments when working on parts of code that contribute to different tasks.  This alone helps with debugging code that isn’t working properly.  After all, it is much easier to locate where a problem is occuring when the code is read in an easily understood way.  Furthermore, it is important to include various print-line statements that output data to the console at specific times in the runtime to ensure values of certain variables are being passed correctly behind the scenes.  By being diligent with these practices, correcting for errors within the application haven’t proven to be very difficult; time spent correcting them is minimal.

When it comes to testing stored procedures in the back-end, it is necessary to include “Call” lines after writing the procedure to allow for a chosen input, as if it were coming as input from the actual application itself, to be tested.  Then, it is necessary to make sure the given input outputs the anticipated result.  The output to verify may come in two forms.  These include a possible table generated by the stored procedure as well as any output variables that the stored procedures may have.  Only when it is known to be giving these correct outputs can it actually be implemented with Java code and corrected for incorrect input that a user may give.  
