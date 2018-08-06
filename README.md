# Binary vote (like/dislike) for furniture articles
### Summary
This is a sample app written in Kotlin that let's the user rate furniture articles by liking or disliking it.
There is an entry screen from which the user can start another voting round. After starting a new voting round a fresh list of articles is loaded from the API and the articles are presented one by one to the user. He can like or dislike the current article and gets forwarded to the next article. Pressing back or up cancels the current voting round and brings the user back to the welcome screen. After rating all the articles and clicking on the "REVIEW" the user is presented a summary of his ratings as list or grid.

The MVI pattern is used that extends the MVVM pattern to encapsulate App specific domain logic into ViewModels that emit mutual exclusive ui states via LiveData.
The use of [Android architecture ViewModel](The use https://developer.android.com/topic/libraries/architecture/viewmodel) allows ui state preserving on orientation change.
The internals of the ViewModel are based on explicit ui states that are inspired by the talk [Managing State with RxJava by Jake Wharton](https://www.youtube.com/watch?v=0IKHxjkgop4).

### Dependencies
* [Kotlin](https://developer.android.com/kotlin)
* [Retrofit 2](http://square.github.io/retrofit) HTTP client to query the API and map requests in a declarative way
* [Moshi](https://github.com/square/moshi) JSON parsing library that can map to Kotlin classes without using reflection
* [RxJava 2](https://github.com/ReactiveX/RxJava) reactive extensions for the JVM to handle asynchronous events in a reactive/stream based way  
* [Koin](https://insert-koin.io/) lightweight Kotlin dependency injection framework that allows simple ViewHolder injection and without code generation
* [Groupie](https://github.com/lisawray/groupie) RecyclerView layout library that implements delegation pattern to easily add new item types   
* [Fresco](http://frescolib.org) Image loading library for loading article images
* [Android arch LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [Android arch ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)

### Changing the article limit
The total amount of articles that are presented to the user in a voting round is defined as const
```
private const val ARTICLE_LIMIT = 10
```
in `Home24Service.kt` and can be changed to a different number.
### Building the app
The App can be build by executing the task assembleDebug with the supplied Gradle wrapper "gradlew" 

### Possible improvements
* Cache category 100 articles response by wrapping the service with a repository that uses a cache
* Adding test to test the ui state transitions of the ViewModels