package com.udacity.asteroidradar

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.getNextWeekDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidsDao
import com.udacity.asteroidradar.database.AppDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class RepositoryTest : TestCase() {

    private lateinit var dao: AsteroidsDao
    private lateinit var db: AppDatabase
    private lateinit var repository: AsteroidsRepository
    private lateinit var asteroids: List<Asteroid>

    @Before
    fun prep() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        dao = db.asteroidsDao

        repository = AsteroidsRepository(db)
        repository.asteroids.observeForever {
            asteroids = it
        }
    }

    @After
    @Throws(IOException::class)
    suspend fun closeDb() = runBlocking {
        dao.clear()
        db.close()
    }
    @Test
    @Throws(Exception::class)
    fun repositoryTest() = runBlocking {
        repository.refreshAsteroids()

        val nights: Array<String> = getNextWeekDates()

        val response = Network.asteroids.getLatestWeek(
            nights[0],
            nights[1],
            "2JabBjC25TuPzOsfWYLBsxyzv6yIZmOT3WmDgIzn"
        ).await()

        val formatted = parseAsteroidsJsonResult(JSONObject(response))

        assertEquals(asteroids[0], formatted[0])

    }

}