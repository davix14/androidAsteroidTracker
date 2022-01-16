package com.example.android.trackmysleepquality

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidsDao
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.DatabaseAsteroids
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AsteroidDatabaseTest: TestCase() {

    private lateinit var asteroidDao: AsteroidsDao
    private lateinit var db: AsteroidsDatabase

    @Before
    public override fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AsteroidsDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        asteroidDao = db.asteroidsDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() = runBlocking {
        val asteroid = DatabaseAsteroids(32332, "Name!", "2021-02-02",
            32.1232, 423.23, 6633.32, 32.3, true)
        asteroidDao.insertAll(asteroid)
        val latest = asteroidDao.getAllAsteroids()
        assertNotNull(latest)
    }
}

