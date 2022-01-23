package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.PictureOfDay


/*@Query("SELECT * FROM databaseasteroids LIMIT 1")
fun getLatestAsteroids(): LiveData<List<DatabaseAsteroids>>*/

@Dao
interface AsteroidsDao {
    @Query("SELECT * FROM databaseasteroids ORDER BY closeApproachDate DESC")
    fun getAllAsteroids(): LiveData<List<DatabaseAsteroids>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroids)

    @Query("DELETE FROM databaseasteroids")
    fun clearAllAsteroids()
}

@Dao
interface PicOfTheDayDao {
    @Query("SELECT * FROM databasepicoftheday")
    fun getPic(): LiveData<DatabasePicOfTheDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pic: DatabasePicOfTheDay)

    @Query("DELETE FROM databasepicoftheday")
    fun clearAllPics()
}


@Database(entities = [
    DatabaseAsteroids::class,
    DatabasePicOfTheDay::class
], version = 2)

abstract class AppDatabase : RoomDatabase() {
    abstract val asteroidsDao: AsteroidsDao
    abstract val picDatabase: PicOfTheDayDao
}

private lateinit var INSTANCE: AppDatabase

fun getDatabase(context: Context): AppDatabase {
    synchronized(AppDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                    AppDatabase::class.java,
                "asteroids"
            ).build()
        }
    }
    return INSTANCE
}