package de.rhm.hotvnot

import android.app.Activity
import android.content.Intent

inline fun <reified T> Activity.startActivity() = startActivity(Intent(this, T::class.java))