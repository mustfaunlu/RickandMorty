package com.unludev.rickandmorty.utils

import android.app.Dialog
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnack(text: String) = Snackbar.make(this, text, 1000).show()

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}