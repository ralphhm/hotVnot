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
        service.getArticlesForCategory()
                .map { ArticlesRating(it.embedded.articles) }
                .map<UiState> { UiState.Rating(it.articleState) }
                .toObservable().startWith(UiState.Loading)
                .subscribe { uiStates.postValue(it) }
    }

}

sealed class UiState {
    object Loading : UiState()
    class Rating(val articleRateState: LiveData<ArticleRateState>) : UiState()
}

class ArticlesRating(private val articles: List<Article>) {
    private var index = 0
    private val likedArticles = LinkedList<String>()

    val articleState = MutableLiveData<ArticleRateState>().apply { postValue(state) }

    private val state
        get() = if (index == articles.size) ArticleRateState.AllRated(articles, likedArticles) else
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

sealed class ArticleRateState {
    class Rating(val imageUrl: String, private val likeCount: Int, private val total: Int, val likeAction: () -> Unit, val dislikeAction: () -> Unit) : ArticleRateState() {
        val likeCounter get() = "$likeCount/$total"
    }

    class AllRated(val articles: List<Article>, val likedArticles: List<String>) : ArticleRateState()
}