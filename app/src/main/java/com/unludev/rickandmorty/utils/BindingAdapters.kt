package com.unludev.rickandmorty.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.unludev.rickandmorty.R

enum class ApiStatus { LOADING, ERROR, DONE }

@BindingAdapter("apiStatus")
fun bindStatus(statusImgView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.DONE -> {
            statusImgView.visibility = View.GONE
        }
        ApiStatus.LOADING -> {
            statusImgView.visibility = View.VISIBLE
            statusImgView.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            statusImgView.visibility = View.VISIBLE
            statusImgView.setImageResource(R.drawable.ic_connection_error)
        }
        else -> {}
    }
}
