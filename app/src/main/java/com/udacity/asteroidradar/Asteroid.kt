package com.udacity.asteroidradar

import android.os.Parcelable
import com.udacity.asteroidradar.database.DatabaseAsteroids
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(val id: Long, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable

fun List<Asteroid>.asDomainModel(): Array<DatabaseAsteroids> {
    return map {
        DatabaseAsteroids(
            id = it.id,
            absoluteMagnitude = it.absoluteMagnitude,
            closeApproachDate = it.closeApproachDate,
            codename = it.codename,
            distanceFromEarth = it.distanceFromEarth,
            estimatedDiameter = it.estimatedDiameter,
            isPotentiallyHazardous = it.isPotentiallyHazardous,
            relativeVelocity = it.relativeVelocity

        )
    }.toTypedArray()
}