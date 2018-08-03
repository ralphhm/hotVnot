package de.rhm.hotvnot.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Article(@Json(name = "sku") val sku: String,
              @Json(name = "title") val title: String,
              @Json(name = "media") val media: List<Media>) {

    @JsonClass(generateAdapter = true)
    class Media(@Json(name = "uri") val uri: String)

    val imageUri get() = media.first().uri

}