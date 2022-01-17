package com.udacity.asteroidradar.api

import androidx.lifecycle.Transformations.map
import com.squareup.moshi.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.DatabaseAsteroids
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import androidx.lifecycle.Transformations

fun Asteroid.asDatabaseModel() {
    return Asteroid.map {
        DatabaseAsteroids(

        )
    }
}
