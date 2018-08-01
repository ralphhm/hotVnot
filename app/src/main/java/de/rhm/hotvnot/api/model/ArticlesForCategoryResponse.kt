package de.rhm.hotvnot.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ArticlesForCategoryResponse(@Json(name = "_embedded") val embedded: Embedded) {

    @JsonClass(generateAdapter = true)
    class Embedded(@Json(name = "articles") val articles: List<Article>)

}