package com.udacity.asteroidradar

import com.squareup.moshi.Json
import com.udacity.asteroidradar.database.DatabasePicOfTheDay

data class PictureOfDay(
    val date: String,
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    val url: String
)

fun PictureOfDay.asDatabseModel(): DatabasePicOfTheDay{
    return DatabasePicOfTheDay(
        date = date,
        mediaType = mediaType,
        title = title,
        url = url
    )
}