package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.asDomainModel
import com.udacity.asteroidradar.api.getNextWeekDates
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class AsteroidsRepository(private val database: AsteroidsDatabase) {

    //  retrieves all data from DB with LiveData to update after changes and maps DB objects to Asteroid objects
    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidsDao.getAllAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val nights = getNextWeekDates()
                val asteroids = Network.getLastWeekFormatted(nights[0], nights[1])
                database.asteroidsDao.insertAll(*asteroids.asDomainModel())
        }
    }

}