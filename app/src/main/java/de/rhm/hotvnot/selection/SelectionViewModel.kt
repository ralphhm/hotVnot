package de.rhm.hotvnot.selection

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import java.util.*
import kotlin.concurrent.schedule

class SelectionViewModel : ViewModel() {

    val states = MutableLiveData<SelectionState>()

    init {
        states.postValue(SelectionState.Loading)
        Timer().schedule(3000) {
            states.postValue(SelectionState.Selection())
        }
    }

}

sealed class SelectionState {
    object Loading : SelectionState()
    class Selection() : SelectionState()
    class Final : SelectionState()
}