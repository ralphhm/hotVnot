package de.rhm.hotvnot.start

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.rhm.hotvnot.R
import de.rhm.hotvnot.selection.SelectionActivity
import de.rhm.hotvnot.startActivity
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        actionStart.setOnClickListener { startActivity<SelectionActivity>() }
    }
}
