package com.udacity.asteroidradar.database

import androidx.lifecycle.Transformations.map
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.PictureOfDay

@Entity
data class DatabasePicOfTheDay constructor(
    @PrimaryKey
    val date: String,
    val mediaType: String,
    val title: String,
    val url: String
)

fun DatabasePicOfTheDay.asDomainModel(): PictureOfDay {
    return PictureOfDay(
        date = date,
        mediaType = mediaType,
        title = title,
        url = url
    )
}
