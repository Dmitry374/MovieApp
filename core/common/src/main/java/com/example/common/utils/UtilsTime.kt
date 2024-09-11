package com.example.common.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun convertDateToFormattedString(releaseDate: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.US)

        val date = inputFormat.parse(releaseDate)
        val calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
        }

        return outputFormat.format(calendar.time).replaceFirstChar {
            it.uppercase()
        }
    } catch (e: Exception) {
        releaseDate
    }
}
