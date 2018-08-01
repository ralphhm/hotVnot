package de.rhm.hotvnot.selection

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import de.rhm.hotvnot.api.Home24Service

class SelectionViewModel(service: Home24Service) : ViewModel() {

    val states = MutableLiveData<SelectionState>()

    init {
        service.getArticlesForCategory()
                .map<SelectionState> { SelectionState.Selection(ArticleSelection(it.embedded.articles.first().media.first().uri), 0, it.embedded.articles.size) }
                .toObservable().startWith(SelectionState.Loading)
                .subscribe { states.postValue(it) }
    }

}

sealed class SelectionState {
    object Loading : SelectionState()
    class Selection(val article: ArticleSelection, val likeCount: Int, val total: Int) : SelectionState() {
        val likeCounter get() = "$likeCount/$total"
    }
    class Final : SelectionState()
}

class ArticleSelection(val url: String, val previous: ArticleSelection? = null, val next: ArticleSelection? = null, val liked: Boolean? = null)