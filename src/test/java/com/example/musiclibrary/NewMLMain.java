package com.example.musiclibrary;

import com.example.musiclibrary.model.Review;
import com.example.musiclibrary.model.Song;
import com.example.musiclibrary.model.SongLength;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.musiclibrary.service.MusicLibraryService.CUSTOM_FORMATTER;

public class NewMLMain {


    public static void logBooleanFlags (ArrayList <Song> songs) {
        ArrayList<Integer> flags = new ArrayList<>();

        for (Song song : songs) {
            if (song.isSelected()) {
                flags.add(1);
            } else {
                flags.add(0);
            }
        }
        // here you print [0,0,1,0,0,1,1,0] as ArrayList
        System.out.println("selected boolean flags: " + flags);
    }


    public static Song selectRandomSong(ArrayList<Song> songs) {
        int range = songs.size();
        Random rand = new Random();
        int randomIndex = rand.nextInt(range);
        return songs.get(randomIndex);
    }


    // 1) create a method to find one or more songs by specifying part of the name (multiple songs can be returned by this search filter).
    // For example, if I search "love", the app should return me all the songs in my library whose title contains "love".
    // 2) write a method to play the song that has been selected - include a mechanism to identify which song has been selected
    // 3) write a method to pause the playing song that has been selected
    // 4) fix small bug in 'next song' and bugs in 'previous song' functionalities
    // 5) write a method to share your library with another user (try to think of what the input parameters would be)
    // 6) add 'search by artist' and 'search by lyrics' functionality
    // 7) add a 'most played' functionality (hint: edit the Song class)
    // 7') change the 'most played' functionality: don't show the songs that have never been played
    // 8) write a method to share a sub-collection of your library with another specific user (use the User class you have already defined)
    // 9) write a method to share a sub-collection of your library with a few friends (add a 'friends' field in your library)
    // 9') privatize all variables and use getters / setters where necessary
    // 9'') include a 'select' functionality. When I select k songs, I want to see somewhere 'k/N' where N is the total number of songs.
    // Make sure k is always <= N no matter how many times select() is called
    // 9''') include a 'deselect' functionality (hint: select() needs a total refactorization because now a song is either selected or not)
    // 10) write a test that randomly selects and deselects songs in your library. log the correct behaviour of the message and the boolean flags
    // the log must be of the form:
    // "number of selected songs: k/N"
    // "'selected' boolean flags: [0,0,1,0,0,1,1,0]" (hint: use logBooleanFlags() method)
    // the 1s are the selected songs, the 0s are the not selected or unselected songs
    // 11) remove the "k/N" message if there are no selected songs
    // 12) include a 'delete songs' functionality. When I select k songs, I can delete them and they get removed from the library

    // remove or refactor the code you have already written in MusicLibrary
    // hint: only the selected songs must be deleted
    // 12') include a 'share songs with friends' functionality. When I select k songs, I can send them to a collection of friends
    // refactor the shareSongsWithFewFriends(...) method: only the selected songs must be shared
    // suppose you have a service able to send a collection of songs to only one friend at a time
    // use it, don't send one song at a time
    // 13) reevaluate the accessibility of the play() method and change it in case
    // 14) add a section for reviews of your app from user, in particular decide if it should be static or not
    // 14') add a functionality to read all the reviews
    // 14'') add a functionality to add a review by a user
    // 14''') add a small subLibrary of songs automatically offered by the app when you download it
    // real songs, complete with lyrics, title, etc. as if the user had downloaded them himself
    // 14'''') add an 'app version' field automatically updated through a method (users can't change it)
    // 15) add the possibility to pick a number of stars from 1 to 5 to a review

    // hint: build a mechanism to make sure a review can only have 1, 2, 3, 4, 5 stars.
    // add a 'thumb up / thumb down' functionality: if the number of stars in a review is 1 or 2, the user must see a thumb down somewhere,
    // if the number of stars in a review is 4 or 5, the user must see a thumb up there.
    // hint1: use a flag variable
    // hint2: think carefully in which class this flag variable should be defined

    // 15') delete the pointless controls in thumbUpOrThumbDown()
    // 15'') remove the static arraylist 'stars' and explain why it's now pointless
    // 15''') add a 'review average' functionality to show when the user sees the app in the playstore;
    // hint: think carefully about the type to consider and where to put the code

    // 16) all reviews need to have date / time of when a user submitted them
    // 16') your app needs to memorize date / time of when a user downloaded his songs
    // 16'') the app must show the downloaded songs starting from the most recent ones,
    // that is, starting from the one whose download is the most recent, then the second most recent and so on
    // hint: use Collections.sort(songsCopy, comparing(Song::getTimesPlayed).reversed());

    // 17) all songs have a length (una durata, in italiano): add this functionality
    // 17') check that seconds and minutes values given to the constructor of SongLength make sense (no negative numbers, no > 60, 0 cases, etc.)
    // hint: use RunTimeException
    // 17'') integrate sleep(3000); in all the methods that play a song
    // 17''') add a 'play always next song' mode
    // hint: define a boolean variable for this mode and a method that combines play() with a loop to switch to the next song after the previous one has been played.
    // integrate this method in all the methods that the user calls to play songs.
    // Don't worry for now about the fact that, when these methods are called, the app keeps playing songs forever with no possible user interaction

    // 18) add a 'play always previous song' mode. Take a closer look at the playSong() method: are you sure the part with the ifs work correctly?
    // hint: remember that the booleans alwaysNextSongModeOn and alwaysPreviewSongModeOn must be incompatible
    // 18') add a 'replay same song' mode

    // 19) use the code that uses the Random class to create a method that returns a randomly chosen song from the ones in your library
    // 19') add a 'play songs randomly' mode

    // 20) it must be impossible to press the next or previous song before selecting a song (typically as soon as the user launches the app the first time)
    // 20') integrate the 'play always previous song', 'play always next song', etc. in the nextSong() method
    // 20'') integrate the 'play always previous song', 'play always next song', etc. in the previousS() method
    // test it with 'play always previous song' mode on
    // 20''') remove code duplicates from ML

    // 21): reorganize all the code below: all the songs and reviews need to be instantiated inside specific methods that return them in a list
    // so they're available when we need them just by calling these methods
    // 21'): integrate the new Spring Boot code into your app: use @Component and @Autowired where it makes sense to use them.
    // 21''): create a new endpoint to add a review from a user.
    // 21'''): create a new endpoint to get in return all the submitted reviews from users.
    // 21''): create a new endpoint to get in return the review average.

    //  22): create a specific package for your controller
    //  22'): all reviews need to be submitted with date & time of when an user sends them
    //  23''): transfer all the review-related functionalities that have so far been contained in ML to a new class ReviewService
    //  23'''): change the ML class so that all the review-related functionalities are delegated to ReviewService
    //  23''''): decide if ReviewService should be instantiated and managed by you or if Spring should do it

    // understand how the Comparable Interface works and changes both the Song class and the
    // Collections.sort(songsCopy, comparing(Song::getTimesPlayed).reversed()) instruction (line 356 of ML).
    // hint: don't give the sort() method a second argument, just let the Songs know how to compare themselves.
    // Verify that the outcome of the mostPlayed() method is the same

    // create a class with methods testing all their functionalities

    // add an endpoint to your app to download a Song from the internet (the song must end up in the central memory of you app)
    // hint: simulate the download process by including the Song object in the body of a HTTP request

    // endpoints for review stuff and endpoints for songs stuff must have their own controllers
    // change the type of the HTTP request to download a song from the internet: from POST to GET
    // add an endpoint to your app to see all the downloaded songs
    // all songs downloaded from the internet must have date and time of download

    // change the endpoint for downloading songs: return the downloaded songs after being updated with download date & time
    // change the method addSong(), allow the storing of collections of songs, not just one

    //  create an endpoint to delete any subgroup of downloaded songs from your library.
    //  integrate the deleteSongs() method with it, but be careful about the conditions it checks to delete them
    //  hint: first of all, take a look at the right annotation to use (see Tor Browser)
    //  hint: what variable of the songs in the body of your http request be initialized? And with what value?

    //  improves the logic of addSongs() method in ML: are there pointless variables?

    //  correct the errors in the showDownloadedSongs(), downloadSongs() controllers

    //  correct the errors in the selectSongsByTitle(), selectSongsByArtist() and selectSongsByLyrics() controllers

    // create and endpoint for selecting a certain number of songs in your library.
    // hint: send an array of indices Integer[] ids representing the songs you want to select as a part of your HTTP request

    // the controller selectedSongs() shouldn't contain a loop, move it to ML

    // Review al the concepts and the code concerning persistence and interaction of Spring with the DB in the pilotes app, then try to explain them to your teacher.

    // Create a 'review' table in the 'raz' database with the same structure of the Review class. (turn on the DB and use DBeaver).
    // Add the required persistence annotations to the Review class and persist a few Reviews on the DB

    //  it makes no sense for a user to have a copy of all the reviews permanently in his local memory.
    //  Reviews should be read when it is requested, but not stored.
    //  Change ReviewService to make it stateless (that is a class with no memory of the data, just the logic to manipulate it)
    //  Change ReviewsController to make it get from the DB all the information it reads from ReviewService'state
    //  hint: what variable in ReviewService is the one that "contacts" the DB?

    //  create a songs table in the DB using DBeaver and edit the Song class accordingly

    //  change the controller & service to add a song in the db when you download one

    //  create a json file with all the songs you have worked so far (Bailando, etc.) and save them in the db

    //  change the delete functionality of your app: when you delete a song, ask the user if he wants to delete the remote file as well
    //  that is the copy of the song in the DB. If he says yes, delete those songs there too.

    // create subpackages for your classes and interfaces as you did for the controllers: service, repository, model. Move the files accordingly

    // right now all the previously downloaded songs are still in the DB, but not in the local memory of your app when it runs.
    //  Change this: when you run your app, the local memory must be synchronized with the remote one.
    //  hint: how did we solve a similar problems for the average of the reviews?

    //  create a json file with all the default songs offered to all users that download the app.
    //  See tor browser to learn how to read a json file from java through Jackson ObjectMapper.

    // add a mechanism to load those songs every time the app starts,
    //  but only if you don't have song records in the DB (that is the very first time you download the app)

    // changes the defaultSongs() method, use the json file you created yesterday to load the default songs
    //  see tor browser and the notes to see how to work with ObjectMapper

    // add 'create your profile' and 'see my profile' complete functionalities: controller, service, repository, entity and DB table.
    //  start with the User class you already defined.

    // remove the throws clause from postConstruct() in MLService, handle the exception internally in defaultSongs()

    // add 'registration date & time', 'address' and 'oldPasswords' fields to User.

    // complete the 'update my profile' functionality.

    // add functionalities to search for songs by title n the DB repository, not locally.
    //  This is to check if a song you have in the local memory of your app is among the downloaded ones in the DB too.
    //  hint: use the same methods you have, but add a parameter from the url after ?
    //  if that parameter is not null, get the records from the DB, otherwise from local memory

    // remove bug previous functionality, allow searches on the DB by only parts of titles
    //  hint: before calling the DB, find the whole title of the song from the string parameter coming from the controller

    // add functionalities to search for artist and lyrics etc. in the DB repository, not locally.
    //  This is to check if a song you have in the local memory of your app is among the downloaded ones in the DB too.
    //  hint: follow the same pattern you used for the 'search by name' functionality above

    // add a myProfile variable in UsersService to have a copy of your data available locally without having to query the DB every time.
    //  Add suitable functionalities to keep it updated and in sync with the DB.
    //  hint: you should be able to change your first and last name, username, password, and address locally, then send these info to the DB

    // when the app starts up, your user profile info must be retrieved from the DB

    // when you change the password, you must check that the new one is different than the last one (if there's an old one)

    // add an automatic age update functionality: add a birthdate and make sure that when the app starts up
    //  the local and remote value of the age field is updated

    // improve the age update functionality: set the new age ONLY when necessary and, in case it is, change the record on the db too

    // include specific rules for the password: only if the password meets these requirements the user can register

    // when a user changes password and it's a valid password, put the current password in the field 'oldPassword'
    //  (continue the code in updateUserProfile() )

    // improve logging messages in services, use only one println message instead of two.
    //  hint: review the grammar of the messages and use the same model Alessandro used.

    // push the code with a both grammatically correct and explicative enough message (Alessandro should review it before pushing).

    // refactor all the services: remove the try / catch blocks and put them in the controllers.
    //  hint: use showDownloadedSongs() as model for the other controller methods.

    // add new functionality: the User class must include information about the songs that user downloaded and has locally in storage.



    // TODO: remove grammatically incorrect log statements and statements that contain objects or list of objects to store in the DB or retrieved from it


    // TODO: remove all useless comments around (the ones with "// etc.")


    // TODO: add functionality to retrieve a user from the DB through its Id.



    // edit the add new user functionality: the old password field must be empty at the beginning, then
    // edit the birthdate field in User: once set, it shouldn't be possible to change it


    // check that all the methods that execute a song increase the timesPlayed index and make sure this variable cannot be set
    // neither at construction time nor via setter.
    // hint: don't use Lombok, use only constructors!


    //  add documentation on all the methods you have created so far in MLService. Use Javadoc (see tor browser)
    //  add what the methods do and write down in a separate file all the classes and tools used to create them
    //  with explanations about what they are and how they are used (this is for interviews)



    // create methods testing all the methods in ML (verify that the ones in MusicLibraryTest work and add others)




    public void newMain() throws InterruptedException {

        //returnFakeReviews();
        //returnFakeSongs()
        MusicLibraryTest music = new MusicLibraryTest();
        music.checkIncreaseTimesPlayed();





        //System.out.println("reviews: " + MusicLibrary.reviews);
        //System.out.println("giftForNewUser: " + MusicLibrary.giftForNewUser);


        //musicLibrary.playSong(selectRandomSong(songs));

        //musicLibrary.playSameSongModeOn();


    }



    public static ArrayList<Song> returnFakeSongs() {

        ArrayList<Song> mySongs = new ArrayList<>();

        Song song1 = Song.builder()
                .songName("Bailando")
                .artist("Enrique Inglesias")
                .lyrics("Yo te miro y se me corta la respiración\n" +
                        "Cuando tú me miras, se me sube el corazón\n" +
                        "(Me palpita lento el corazón)\n" +
                        "Y en un silencio tu mirada dice mil palabras (uh)\n" +
                        "La noche en la que te suplico que no salga el sol\n" +
                        "Bailando (bailando)\n")
                .timesPlayed(20)
                .addedOn(LocalDateTime.of(2010, 2, 2, 18, 9))
                .addedOnStringFormat(LocalDateTime.of(2010, 2, 2, 18, 9).format(CUSTOM_FORMATTER))
                .length(new SongLength(0, 3, 47))
                .build();
        Song song2 = Song.builder()
                .songName("Subeme La Radio")
                .artist("Enrique Inglesias")
                .lyrics("Súbeme la radio que esta es mi canción\n" +
                        "Siente el bajo que va subiendo\n" +
                        "Tráeme el alcohol que quita el dolor\n" +
                        "Hoy vamos a juntar la luna y el sol ")
                .timesPlayed(23)
                .addedOn(LocalDateTime.of(2020, 8, 26, 16, 30))
                .addedOnStringFormat(LocalDateTime.of(2020, 8, 26, 16, 30).format(CUSTOM_FORMATTER))
                .length(new SongLength(0, 3, 53))
                .build();
        Song song3 = Song.builder()
                .songName("Craigie Hill")
                .artist("Cara Dillon")
                .lyrics("It being in the springtime and the small\n" +
                        "Birds they were singing\n" +
                        "Down by yon shady harbour I carelessly\n" +
                        "Did stray\n" +
                        "The the thrushes they were warbling")
                .timesPlayed(0)
                .addedOn(LocalDateTime.of(2000, 1, 1, 1, 2))
                .addedOnStringFormat(LocalDateTime.of(2000, 1, 1, 1, 2).format(CUSTOM_FORMATTER))
                .length(new SongLength(0, 5, 30))
                .build();
        Song song4 = Song.builder()
                .songName("Country Boy")
                .artist("Alan Jackson")
                .lyrics("Excuse me ma'am, I saw you walkin'\n" +
                        "I turned around, I'm not a stalker\n" +
                        "Where you going? Maybe I can help you\n" +
                        "My tank is full, I'd be obliged to take you")
                .timesPlayed(0)
                .addedOn(LocalDateTime.of(2000, 1, 1, 1, 1))
                .addedOnStringFormat(LocalDateTime.of(2000, 1, 1, 1, 1).format(CUSTOM_FORMATTER))
                .length(new SongLength(0, 4, 3))
                .build();
        Song song5 = Song.builder()
                .songName("Limbo")
                .artist("Daddy Yankee")
                .lyrics("Vamos cógele el ritmo, cintura, rodilla al piso\n" +
                        "Baja y pasa el limbo (nos fuimo' a fuego)\n" +
                        "Vamos cógele el ritmo, cintura, rodilla al piso\n" +
                        "Baja y pasa el limbo (limbo)")
                .timesPlayed(8)
                .addedOn(LocalDateTime.of(2017, 10, 30, 8, 30))
                .addedOnStringFormat(LocalDateTime.of(2017, 10, 30, 8, 30).format(CUSTOM_FORMATTER))
                .length(new SongLength(0, 4, 15))
                .build();
        Song song6 = Song.builder()
                .songName("El Perdòn")
                .artist("Nicky Jam y Enrique Inglesias")
                .lyrics("Dime si es verdad\n" +
                        "Me dijeron que te estás casando\n" +
                        "Tú no sabes cómo estoy sufriendo\n" +
                        "Esto te lo tengo que decir\n" +
                        "Cuéntame, tu despedida para mí fue dura\n" +
                        "Será que te llevó a la luna\n" +
                        "Y yo no supe hacerlo así")
                .timesPlayed(3)
                .addedOn(LocalDateTime.of(2016, 8, 26, 16, 30))
                .addedOnStringFormat(LocalDateTime.of(2016, 8, 26, 16, 30).format(CUSTOM_FORMATTER))
                .length(new SongLength(0, 3, 47))
                .build();
        Song song7 = Song.builder()
                .songName("International Love")
                .artist("Pitbull")
                .lyrics("You can't catch me boy (can't catch me boy)\n" +
                        "I am overseas at about a hundred Gs for sure\n" +
                        "Don't test me boy (don't test me boy) 'cause I rap with the best\n" +
                        "Fo' sho three oh five to the death of me\n" +
                        "Cremate my body let the ocean have what's left of me\n" +
                        "But for now forget about that\n" +
                        "Blow the whistle, baby you're the referee")
                .timesPlayed(7)
                .addedOn(LocalDateTime.now())
                .addedOnStringFormat(LocalDateTime.now().format(CUSTOM_FORMATTER))
                .length(new SongLength(0, 4, 9))
                .build();
        Song song8 = Song.builder()
                .songName("Me Voy Enamorando ft. Farruko (remix)")
                .artist("Chino & Nacho")
                .lyrics("Me caes bien\n" +
                        "Sé que yo a ti también\n" +
                        "¿Por qué no nos besamos?\n" +
                        "Y vemos cómo vamos\n" +
                        "Puedo esperar\n" +
                        "Si lo quieres pensar\n" +
                        "Si quieres, mientras tanto\n" +
                        "Me voy enamorando\n" +
                        "Me voy enamorando (uoh, uoh)\n" +
                        "Me voy enamorando (uoh, uoh)\n" +
                        "Me voy enamorando (uoh, uoh)\n" +
                        "Me voy enamorando (uoh-uoh)")
                .timesPlayed(9)
                .addedOn(LocalDateTime.of(2018, 5, 1, 16, 5))
                .addedOnStringFormat(LocalDateTime.of(2018, 5, 1, 16, 5).format(CUSTOM_FORMATTER))
                .length(new SongLength(0, 5, 49))
                .build();
        Song song9 = Song.builder()
                .songName("Zalele ft. Asu & Ticy")
                .artist("Cláudya")
                .lyrics("Cand iti sarut buzele\n" +
                        "Of zale zalele\n" +
                        "Ma trec toate apele\n" +
                        "Of zale zalele\n" +
                        "Cand imi saruti buzele\n" +
                        "Of zale zalele")
                .timesPlayed(44)
                .addedOn(LocalDateTime.of(2021, 6, 9, 10, 35))
                .addedOnStringFormat(LocalDateTime.of(2021, 6, 9, 10, 35).format(CUSTOM_FORMATTER))
                .length(new SongLength(0, 3, 42))
                .build();
        Song song10 = Song.builder()
                .songName("Dragostea Din Tei")
                .artist("O-Zone")
                .lyrics("Alo, salut, sunt eu, un haiduc\n" +
                        "Și te rog, iubirea mea, primește fericirea\n" +
                        "Alo, alo, sunt eu Picasso\n" +
                        "Ți-am dat beep, și sunt voinic\n" +
                        "Dar sa știi, nu-ți cer nimic")
                .timesPlayed(21)
                .addedOn(LocalDateTime.of(2023, 2, 23, 6, 30))
                .addedOnStringFormat(LocalDateTime.of(2023, 2, 23, 6, 30).format(CUSTOM_FORMATTER))
                .length(new SongLength(0, 4, 46))
                .build();

        mySongs.addAll(List.of(song1, song2, song3, song4, song5, song6, song7, song8, song9, song10));

        return mySongs;
    }


    public static ArrayList<Review> returnFakeReviews() {

        ArrayList<Review> allReviews = new ArrayList<>();

        Review review1 = Review.builder()
                .text("Nice App")
                .stars(4)
                .submittedOn(LocalDateTime.now())
                .submittedOnStringFormat(LocalDateTime.now().format(CUSTOM_FORMATTER))
                .build();
        Review review2 = Review.builder()
                .text("This app is ok")
                .stars(3)
                .submittedOn(LocalDateTime.now())
                .submittedOnStringFormat(LocalDateTime.now().format(CUSTOM_FORMATTER))
                .build();
        Review review3 = Review.builder()
                .text("I love this app")
                .stars(5)
                .submittedOn(LocalDateTime.of(2023, 8, 6, 12, 30))
                .submittedOnStringFormat(LocalDateTime.of(2023, 8, 6, 12, 30).format(CUSTOM_FORMATTER))
                .build();
        Review review4 = Review.builder()
                .text("This App sucks :( !!!")
                .stars(3)
                .build();
        Review review5 = Review.builder()
                .text("This App rocks :) !!!")
                .stars(5)
                .build();
        Review review6 = Review.builder()
                .text("I love this app")
                .stars(5)
                .build();
        Review review7 = Review.builder()
                .text("THIS APP IS DOG SHIT")
                .stars(1)
                .build();
        Review review8 = Review.builder()
                .text("I love this app")
                .stars(5)
                .build();

        allReviews.addAll(List.of(review1, review2, review3, review4, review5, review6, review7, review8));

        return allReviews;
    }












    /*

    // TEST VARI:












}


     */
}