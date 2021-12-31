# Stream-ON
### Video Demo:  <URL HERE>
### Description:
Final Project for CS50 - 2021: An Android Application that helps users find where to stream their favorite movies.

Select your streaming services and search for movies. Stream-ON will return results if the movie is available on one of your services.
If you click on any of the movie results, it will also tell you where it is available.


### Important Information:
The API that was used for this app is a Freemium API which means it has some limitations on the free version.
Stream-ON can only do 100 requests per day and after that will return an error message. This means you will need to wait one day before you can continue the use of the App.
Each service adds another request to the search. So for testing purposes I would recommend to only use 2-4 services.

### API Used:
https://rapidapi.com/movie-of-the-night-movie-of-the-night-default/api/streaming-availability/


## Created Kotlin Files

#### AuthenticationInterceptor.kt
Purpose: Authentication Interceptor holds the information about the API Authentication.
This is needed to connect to the API.

#### MovieAPIInterface.kt
Purpose: API Interface that describes which API calls are available and which information
is needed in order to call it.
Only one API call is available: Basic Search

#### MovieAPIResponseClass.kt
Purpose: Response Class that describes how the returned JSON is designed.
This class makes it possible to use the returned JSON.

#### AppModule.kt
Purpose: Helper Module in order to create a HTTP Client and an API Service, which is needed to make API calls

#### CardAdapter.kt
Purpose: Card Adapter binds each movie into a "card" on the Main Activity
This is needed in order to use Android's Recycler View

#### CardViewHolder.kt
Purpose: Card View Holder is used to show what is inside of each card.
The title and description and the image are bound to a "Card"


#### MainActivity.kt
Purpose: Activity for the home and search page. This is the Activity that is being started when the
app opens. This Activity has the functionality to search and show movies.
API Calls are being executed from here.


#### MovieClass.kt
Purpose: MovieClass is a description of my custom data class of a Movie.
It describes what information is available / needed for each movie.

#### MovieClickListener.kt
Purpose: MovieClickListener is a simple custom click listener that tells my app
which movie was clicked on exactly.

#### MovieDetailActivity.kt
Purpose: Activity for the detail page. This is the Activity that is being started when the
user clicks on any movie. This Activity has the functionality to display movies.
API Calls are NOT being executed from here.

#### MovieSettingsActivity.kt
Purpose: Activity for the settings page. This is the Activity that is being started when the
user opens the app for the first time or when the user clicks on the settings icon in the nav bar.
This Activity has the functionality to display and save the user's settings.
API Calls are NOT being executed from here.


## Created XML Files
#### strings.xml
Holds a list of all the used text in the Stream-ON Application

#### activity_main.xml
XML that describes how the main page of the application looks. It's a Linear Layout that shows the navigation bar with a settings icon.
It also has the search bar with a search button and a Recycler View beneath it.
The Recycler View is used to show movie cards.
I have used the Recycler View as the movies that are being returned are dynamic and the number of returned movies can vary.

#### card_cell.xml
XML that describes how each card looks. It's a simple Linear Layout that can display an image and two TextViews (Title and description).
This card is used inside of the Recycler View and can be displayed multiple times inside of it.

#### activity_movie_detail.xml
XML that describes how the detail page looks. Scrollview is used to show static data.
It can display an image some text and a table of text to show the movie details.

#### activity_settings.xml
XML that describes how the settings pake looks. This page is a Linear Layout as it simply shows a group of radio buttons for the available countries and a group of checkboxes for the services.

