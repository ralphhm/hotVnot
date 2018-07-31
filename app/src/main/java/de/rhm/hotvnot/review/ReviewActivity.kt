package de.rhm.hotvnot.review

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.rhm.hotvnot.R
import kotlinx.android.synthetic.main.activity_review.*

class ReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
