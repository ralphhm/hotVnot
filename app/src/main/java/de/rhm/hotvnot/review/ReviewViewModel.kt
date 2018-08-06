package de.rhm.hotvnot.review

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.DrawableRes
import de.rhm.hotvnot.R
import de.rhm.hotvnot.api.Home24Service

class ReviewViewModel(service: Home24Service, private val likedArticleSkus: List<String>) : ViewModel() {

    val uiStates = MutableLiveData<UiState>()

    init {
        service.getArticlesForCategory100()
                .map { it.embedded.articles }
                .map { articles -> articles.map { RatedArticle(it.title, it.imageUri, likedArticleSkus.contains(it.sku)) } }
                .map<UiState> { articles -> UiState.Result(articles, Presentation.List) { resultState -> uiStates.postValue(resultState) } }
                .toObservable().startWith(UiState.Loading)
                .subscribe { uiStates.postValue(it) }
    }

    sealed class UiState {
        object Loading : UiState()
        class Result(private val articles: List<RatedArticle>, val presentation: Presentation, private val onSwitchPresentation: (Result) -> Unit) : UiState() {
            val articleItems = articles.map { it.toItem(presentation) }
            val switchPresentation = { onSwitchPresentation.invoke(Result(articles, presentation.opposite(), onSwitchPresentation)) }
        }
    }

}

fun RatedArticle.toItem(presentation: Presentation) = when (presentation) {
    Presentation.Grid -> ArticleGridItem(this)
    Presentation.List -> ArticleListItem(this)
}

class RatedArticle(val title: String, val imageUrl: String, val liked: Boolean)

sealed class Presentation(val columnCount: Int, @DrawableRes val oppositeActionIcon: Int) {

    abstract fun opposite(): Presentation

    object Grid : Presentation(2, R.drawable.ic_view_list_black_24dp) {
        override fun opposite() = List
    }

    object List : Presentation(1, R.drawable.ic_view_module_black_24dp) {
        override fun opposite() = Grid
    }
}

