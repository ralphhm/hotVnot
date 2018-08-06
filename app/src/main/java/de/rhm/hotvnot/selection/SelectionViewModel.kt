package de.rhm.hotvnot.selection

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import de.rhm.hotvnot.api.Home24Service
import de.rhm.hotvnot.api.model.Article
import java.util.*

class SelectionViewModel(service: Home24Service) : ViewModel() {

    val uiStates = MutableLiveData<UiState>()

    init {
        service.getArticlesForCategory100()
                .map { ArticlesRating(it.embedded.articles) }
                .map<UiState> { UiState.Rating(it.articleState) }
                // emit loading state while fetching content
                .toObservable().startWith(UiState.Loading)
                .subscribe { uiStates.postValue(it) }
    }

    /**
     * Main states for the selection screen
     */
    sealed class UiState {

        /**
         * Content is being loaded
         */
        object Loading : UiState()

        /**
         * Content was loaded and is ready to be rated
         */
        class Rating(val articleRateState: LiveData<ArticleRateState>) : UiState()
    }

}

/**
 * Stateful model that represents the current voting state and allows user to initiate state transitions though actions via the sub-states of ArticleRateState
 */
class ArticlesRating(private val articles: List<Article>) {
    private var index = 0
    private val likedArticles = LinkedList<String>()

    /**
     * LiveData of current article rate state
     */
    val articleState = MutableLiveData<ArticleRateState>().apply { postValue(state) }

    private val state
        get() = if (index == articles.size) ArticleRateState.AllRated(likedArticles) else
            ArticleRateState.Rating(articles[index].imageUri, likedArticles.size, articles.size, { like() }, { dislike() })

    private fun like() {
        likedArticles.add(articles[index].sku)
        index++
        articleState.postValue(state)
    }

    private fun dislike() {
        index++
        articleState.postValue(state)
    }
}

/**
 * Sub-states for the UiState#Rating state
 */
sealed class ArticleRateState {

    /**
     * An article needs to be rated
     */
    class Rating(val imageUrl: String, private val likeCount: Int, private val total: Int, val likeAction: () -> Unit, val dislikeAction: () -> Unit) : ArticleRateState() {
        val likeCounter get() = "$likeCount/$total"
    }

    /**
     * All articles are rated
     */
    class AllRated(val likedArticles: List<String>) : ArticleRateState()
}