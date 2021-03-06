package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
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
        @Query("end_date") endDate: String
    ): Deferred<String>

    //  pic of the day get request
    @GET("/planetary/apod")
    fun getPicOfTheDay(): Deferred<String>
}

//val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

object Network {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
//        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(
            OkHttpClient.Builder()
                .addInterceptor{
                    val url = it
                        .request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("api_key", Constants.API_KEY)
                        .build()
                    it.proceed(it.request().newBuilder().url(url).build())
                }.build()
        )
        .build()


    val asteroids = retrofit.create(AsteroidApiService::class.java)

    suspend fun getLastWeekFormatted(startDate: String, endDate: String): List<Asteroid> {
        val rawResponse =
            asteroids.getLatestWeek(startDate, endDate).await()
        return parseAsteroidsJsonResult(JSONObject(rawResponse))
    }

    suspend fun getPicOfTheDay(): PictureOfDay {
        val rawResponse = asteroids.getPicOfTheDay().await()
        return parsePicJsonResponse(JSONObject(rawResponse))
    }
}