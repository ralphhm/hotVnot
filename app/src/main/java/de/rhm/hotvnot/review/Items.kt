package de.rhm.hotvnot.review

import com.xwray.groupie.databinding.BindableItem
import de.rhm.hotvnot.R
import de.rhm.hotvnot.databinding.ItemArticleGridBinding
import de.rhm.hotvnot.databinding.ItemArticleListBinding

class ArticleGridItem(val article: RatedArticle) : BindableItem<ItemArticleGridBinding>() {
    override fun getLayout() = R.layout.item_article_grid

    override fun bind(viewBinding: ItemArticleGridBinding, position: Int) {
        viewBinding.article = article
    }

}

class ArticleListItem(val article: RatedArticle) : BindableItem<ItemArticleListBinding>() {
    override fun getLayout() = R.layout.item_article_list

    override fun bind(viewBinding: ItemArticleListBinding, position: Int) {
        viewBinding.article = article
    }
}