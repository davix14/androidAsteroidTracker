package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface AsteroidApiService {
    //  get request for data
    @GET("/neo/rest/v1/feed")
    fun getLatestWeek(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Deferred<String>
}

object Network {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()


    val asteroids = retrofit.create(AsteroidApiService::class.java)

    suspend fun getLastWeekFormatted(startDate: String, endDate: String): List<Asteroid>{
        val rawResponse = Network.asteroids.getLatestWeek(startDate, endDate, Constants.API_KEY).await()
        return parseAsteroidsJsonResult(JSONObject(rawResponse))
    }
}