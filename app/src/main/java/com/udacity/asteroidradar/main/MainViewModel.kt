package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

//    var asteroids = MutableLiveData<List<Asteroid>>()

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()

    val navigateToAsteroidDetail: LiveData<Asteroid>
        get() = _navigateToAsteroidDetail

    val db = getDatabase(application)
    val repository = AsteroidsRepository(db)

    init {
        viewModelScope.launch {
            repository.refreshAsteroids()
        }
    }

    val asteroids = repository.asteroids

    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToAsteroidDetail.value = asteroid
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToAsteroidDetail.value = null
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

/*
*
*  asteroids.value = listOf(
            Asteroid(
                32332, "Name!", "2021-02-02",
                32.1232, 423.23, 6633.32, 32.3, true
            ),
            Asteroid(
                1242, "NAME2!", "2021-02-03",
                32.1232, 423.23, 6633.32, 32.3, false
            )
        )
        * */