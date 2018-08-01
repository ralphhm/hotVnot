package de.rhm.hotvnot.api

import de.rhm.hotvnot.api.model.ArticlesForCategoryResponse
import io.reactivex.Single
import retrofit2.http.GET

private const val ARTICLE_LIMIT = 10

interface Home24Service {

    @GET("categories/100/articles?appDomain=1&locale=de_DE&limit=$ARTICLE_LIMIT")
    fun getArticlesForCategory(): Single<ArticlesForCategoryResponse>
}