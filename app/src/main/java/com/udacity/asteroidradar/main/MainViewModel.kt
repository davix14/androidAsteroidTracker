package com.udacity.asteroidradar.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid

class MainViewModel : ViewModel() {

    var asteroids = MutableLiveData<List<Asteroid>>()

    init {
        asteroids.value = listOf(
            Asteroid(
                32332, "Name!", "2021-02-02",
                32.1232, 423.23, 6633.32, 32.3, true
            ),
            Asteroid(
                1242, "NAME2!", "2021-02-03",
                32.1232, 423.23, 6633.32, 32.3, false
            )
        )
    }
}