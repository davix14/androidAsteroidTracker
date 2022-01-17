package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid


/*@Query("SELECT * FROM databaseasteroids LIMIT 1")
fun getLatestAsteroids(): LiveData<List<DatabaseAsteroids>>*/

@Dao
interface AsteroidsDao {
    @Query("SELECT * FROM databaseasteroids")
    fun getAllAsteroids(): LiveData<List<DatabaseAsteroids>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroids)

    @Query("DELETE FROM databaseasteroids")
    suspend fun clear()
}


@Database(entities = [DatabaseAsteroids::class], version = 1)
abstract class AsteroidsDatabase : RoomDatabase() {
    abstract val asteroidsDao: AsteroidsDao
}

private lateinit var INSTANCE: AsteroidsDatabase

fun getDatabase(context: Context): AsteroidsDatabase {
    synchronized(AsteroidsDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidsDatabase::class.java,
                "asteroids"
            ).build()
        }
    }
    return INSTANCE
}