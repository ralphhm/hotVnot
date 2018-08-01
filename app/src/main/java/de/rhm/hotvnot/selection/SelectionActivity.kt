package de.rhm.hotvnot.selection

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import de.rhm.hotvnot.R
import de.rhm.hotvnot.review.ReviewActivity
import de.rhm.hotvnot.startActivity
import kotlinx.android.synthetic.main.activity_selection.*
import kotlinx.android.synthetic.main.content_selection.*
import org.koin.android.architecture.ext.viewModel

class SelectionActivity : AppCompatActivity() {

    private val viewModel by viewModel<SelectionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel.states.observe(this, Observer { onStateChanged(it!!) })
        actionReview.setOnClickListener { startActivity<ReviewActivity>() }
    }

    private fun onStateChanged(state: SelectionState) {
        loadingContent.visibility = if (state is SelectionState.Loading) VISIBLE else GONE
        selectionGroup.visibility = if (state is SelectionState.Selection) VISIBLE else GONE
        actionReview.visibility = if (state is SelectionState.Final) VISIBLE else GONE
    }

}
