package de.rhm.hotvnot.selection

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.rhm.hotvnot.R
import de.rhm.hotvnot.review.ReviewActivity
import de.rhm.hotvnot.startActivity
import kotlinx.android.synthetic.main.activity_selection.*
import kotlinx.android.synthetic.main.content_selection.*

class SelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionReview.setOnClickListener { startActivity<ReviewActivity>() }
    }

}
