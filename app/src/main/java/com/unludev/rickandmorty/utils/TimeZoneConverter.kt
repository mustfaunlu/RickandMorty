package com.unludev.rickandmorty.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale("tr"))
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date)
}