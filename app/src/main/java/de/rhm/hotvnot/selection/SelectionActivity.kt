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
        viewModel.uiStates.observe(this, Observer { onStateChanged(it!!) })
        actionReview.setOnClickListener { startActivity<ReviewActivity>() }
    }

    private fun onStateChanged(state: UiState) {
        loadingContent.visibility = if (state is UiState.Loading) VISIBLE else GONE
        if (state is UiState.Rating) {
            state.articleRateState.observe(this@SelectionActivity, Observer { onArticleRateStateChanged(it!!) })
        } else {
            actionReview.visibility = GONE
            selectionGroup.visibility = GONE
        }
    }

    private fun onArticleRateStateChanged(state: ArticleRateState) = when (state) {
        is ArticleRateState.Rating -> {
            selectionGroup.visibility = VISIBLE
            actionReview.visibility = GONE
            articleImage.setImageURI(state.imageUrl)
            counter.text = state.likeCounter
            actionLike.setOnClickListener { state.likeAction.invoke() }
            actionDislike.setOnClickListener { state.dislikeAction.invoke() }
        }
        is ArticleRateState.AllRated -> {
            selectionGroup.visibility = GONE
            actionReview.visibility = VISIBLE
            actionReview.setOnClickListener { startActivity<ReviewActivity>() }
        }
    }

}
