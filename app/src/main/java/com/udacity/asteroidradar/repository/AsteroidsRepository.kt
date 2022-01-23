package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.getNextWeekDates
import com.udacity.asteroidradar.asDatabaseModel
import com.udacity.asteroidradar.asDatabseModel
import com.udacity.asteroidradar.database.AppDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidsRepository(private val AppDatabase: AppDatabase) {

    //  retrieves all data from DB with LiveData to update after changes and maps DB objects to Asteroid objects
    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(AppDatabase.asteroidsDao.getAllAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val nights = getNextWeekDates()
            var retryCount = 0
            for (i in 1..3) {
                try {
                    if (i > 1) {
                        retryCount++
                    }
                    val asteroids = Network.getLastWeekFormatted(nights[0], nights[1])
                    AppDatabase.asteroidsDao.insertAll(*asteroids.asDatabaseModel())
                    Log.d("debugin", "POSSIBLE SUCCESS?")
                    break
                } catch (e: Exception) {
                    Log.d("debugin", "${e.printStackTrace()}")
                    Log.d("debugin", "ERROR! Retrying Retry#: $retryCount")
                }
            }
        }
    }

    val picOfTheDaySrc: LiveData<PictureOfDay> =
        Transformations.map(AppDatabase.picDatabase.getPic()) {
            it?.asDomainModel()
        }

    suspend fun refreshPicOfTheDay() {
        withContext(Dispatchers.IO) {
            var retryCount = 0

            for (i in 1..3) {
                try {
                    if (i > 1) {
                        retryCount++
                    }
                    val pic = Network.getPicOfTheDay()
                    AppDatabase.picDatabase.clearAllPics()
                    AppDatabase.picDatabase.insert(pic.asDatabseModel())
                    Log.d("debugin", "POSSIBLE SUCCESS?")
                    break
                } catch (e: Exception) {
                    Log.d("debugin", "${e.printStackTrace()}")
                    Log.d(
                        "debugin",
                        "ERROR GETTING PICTURE DATA FROM NASA POTD! Retrying Retry#: $retryCount"
                    )
                }
            }
        }
    }

}