package de.rhm.hotvnot.selection

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import de.rhm.hotvnot.api.Home24Service
import de.rhm.hotvnot.api.model.Article

class SelectionViewModel(service: Home24Service) : ViewModel() {

    val states = MutableLiveData<SelectionState>()

    init {
        service.getArticlesForCategory()
                .map<SelectionState> { SelectionState.Selection(it.embedded.articles) }
                .toObservable().startWith(SelectionState.Loading)
                .subscribe { states.postValue(it) }
    }

}

sealed class SelectionState {
    object Loading : SelectionState()
    class Selection(val articles: List<Article>) : SelectionState()
    class Final : SelectionState()
}