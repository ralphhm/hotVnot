package de.rhm.hotvnot

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(listOf(AppModule))
        Fresco.initialize(this)
    }
}