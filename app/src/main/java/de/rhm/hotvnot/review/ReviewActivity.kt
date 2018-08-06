package de.rhm.hotvnot.review

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View.GONE
import android.view.View.VISIBLE
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import de.rhm.hotvnot.R
import de.rhm.hotvnot.review.ReviewViewModel.UiState.Loading
import de.rhm.hotvnot.review.ReviewViewModel.UiState.Result
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.android.synthetic.main.content_review.*
import org.koin.android.architecture.ext.viewModel

const val EXTRA_LIKED_ARTICLES_SKUS = "likedArticleSkus"

class ReviewActivity : AppCompatActivity() {

    private val viewModel by viewModel<ReviewViewModel>(parameters = { mapOf("skus" to intent.getStringArrayListExtra(EXTRA_LIKED_ARTICLES_SKUS)) })
    private val section = Section()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        articleList.adapter = GroupAdapter<ViewHolder>().apply {
            add(section)
        }
        viewModel.uiStates.observe(this, Observer { onStateChanged(it!!) })
    }

    private fun onStateChanged(state: ReviewViewModel.UiState) {
        loadingContent.visibility = if (state is Loading) VISIBLE else GONE
        articleList.visibility = if (state is Result) VISIBLE else GONE
        fab.visibility = if (state is Result) VISIBLE else GONE
        if (state is Result) {
            (articleList.layoutManager as GridLayoutManager).spanCount = state.presentation.columnCount
            section.update(state.articleItems)
            fab.setImageResource(state.presentation.oppositeActionIcon)
            fab.setOnClickListener { state.switchPresentation.invoke() }
        }
    }

}

fun Activity.startReviewActivity(skus: ArrayList<String>) = startActivity(Intent(this, ReviewActivity::class.java).apply { putStringArrayListExtra(EXTRA_LIKED_ARTICLES_SKUS, skus) })
