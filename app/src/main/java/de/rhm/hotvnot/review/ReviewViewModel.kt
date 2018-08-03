package de.rhm.hotvnot.review

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import de.rhm.hotvnot.api.Home24Service
import de.rhm.hotvnot.api.model.Article

class ReviewViewModel(service: Home24Service, private val likedArticleSkus: List<String>) : ViewModel() {

    val uiStates = MutableLiveData<UiState>()

    init {
        service.getArticlesForCategory100()
                .map { it.embedded.articles }
                .map<UiState> { articles -> UiState.Result(articles.toArticleItems(true), 1) }
                .toObservable().startWith(UiState.Loading)
                .subscribe { uiStates.postValue(it) }
    }

    sealed class UiState {
        object Loading : UiState()
        class Result(val articleItems: List<ArticleItem>, val columnCount: Int) : UiState()
    }

    private fun List<Article>.toArticleItems(isListItem: Boolean) = map { it.toArticleItem(isListItem) }

    private fun Article.toArticleItem(isListItem: Boolean) = ArticleItem(imageUri, likedArticleSkus.contains(sku), isListItem)

}

class ArticleItem(val imageUrl: String, val selected: Boolean, var list: Boolean)

