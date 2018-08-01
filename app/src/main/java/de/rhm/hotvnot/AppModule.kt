package de.rhm.hotvnot

import de.rhm.hotvnot.selection.SelectionViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

val AppModule = applicationContext {

    viewModel { SelectionViewModel() }

}