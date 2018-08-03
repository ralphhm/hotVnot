package de.rhm.hotvnot.review

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xwray.groupie.databinding.BindableItem
import de.rhm.hotvnot.R
import de.rhm.hotvnot.api.Home24Service
import de.rhm.hotvnot.api.model.Article
import de.rhm.hotvnot.databinding.ItemArticleListBinding

class ReviewViewModel(service: Home24Service, private val likedArticleSkus: List<String>) : ViewModel() {

    val uiStates = MutableLiveData<UiState>()

    init {
        service.getArticlesForCategory100()
                .map { it.embedded.articles }
                .map<UiState> { articles -> UiState.Result(articles.toArticleItems(true), 1) }
                .toObservable().startWith(UiState.Loading)
                .subscribe { uiStates.postValue(it) }
    }

    sealed class UiState {
        object Loading : UiState()
        class Result(val articleItems: List<ArticleListItem>, val columnCount: Int) : UiState()
    }

    private fun List<Article>.toArticleItems(isListItem: Boolean) = map { it.toArticleItem(isListItem) }

    private fun Article.toArticleItem(isListItem: Boolean) = ArticleListItem(ArticleModel(title, imageUri, likedArticleSkus.contains(sku)))

}

class ArticleModel(val title: String, val imageUrl: String, val selected: Boolean)

class ArticleListItem(val article: ArticleModel) : BindableItem<ItemArticleListBinding>() {
    override fun getLayout() = R.layout.item_article_list

    override fun bind(viewBinding: ItemArticleListBinding, position: Int) {
        viewBinding.article = article
    }
}

