package com.udacity.asteroidradar.api

import com.squareup.moshi.*
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import java.util.*

@JsonClass(generateAdapter = true)
data class NetworkAsteroidsContainer(val content: Content)

@JsonClass(generateAdapter = true)
data class Content(
    val links: List<String>,
    val element_count: Number,
    val near_earth_objects: List<List<String>>
)

internal object JSONObjectAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): JSONObject? {
        // Here we're expecting the JSON object, it is processed as Map<String, Any> by Moshi
        return (reader.readJsonValue() as? Map<String, Any>)?.let { data ->
            JSONObject(data)
        }
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: JSONObject?) {
        value?.let { writer.value(Buffer().writeUtf8(value.toString())) }
    }
}
