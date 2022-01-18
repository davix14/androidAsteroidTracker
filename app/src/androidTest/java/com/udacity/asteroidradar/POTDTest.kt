package com.udacity.asteroidradar

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.PicOfTheDayResponse
import com.udacity.asteroidradar.api.getNextWeekDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class POTDTest: TestCase() {
    @Test
    @Throws(Exception::class)
    fun getPOTDTest() = runBlocking {

        var response = Network.asteroids.getPicOfTheDay(Constants.API_KEY).await()

        Log.d("testING", "Response: $response")

        assertNotNull(response)
    }
}