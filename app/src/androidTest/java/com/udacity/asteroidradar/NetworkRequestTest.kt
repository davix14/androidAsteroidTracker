package com.udacity.asteroidradar

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.getNextWeekDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NetworkRequestTest : TestCase() {

    @Test
    @Throws(Exception::class)
    fun getRequestTest() = runBlocking {
        val nights: Array<String> = getNextWeekDates()

        val response = Network.asteroids.getLatestWeek(
            nights[0],
            nights[1],
            "2JabBjC25TuPzOsfWYLBsxyzv6yIZmOT3WmDgIzn"
        ).await()

        val formatted = parseAsteroidsJsonResult(JSONObject(response))

        Log.d("testING", "Response: $formatted")
        assertNotNull(response[0])
    }
}