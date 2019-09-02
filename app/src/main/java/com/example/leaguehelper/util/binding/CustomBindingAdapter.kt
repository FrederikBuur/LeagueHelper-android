package com.example.leaguehelper.util.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.leaguehelper.util.setImageUrl

class CustomBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImageUrl(imageView: ImageView, url: String?) {
            url?.let {
                imageView.setImageUrl(it)
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["fetchMoreCallback", "isFetching"], requireAll = true)
        fun setLoadMoreCallBack(recyclerView: RecyclerView, fetchMore: () -> Unit, isFetching: ObservableBoolean) {

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    (recyclerView.layoutManager as? LinearLayoutManager)?.let { lm ->

                        val totalCount = lm.itemCount
                        val lastVisibleItem = lm.findLastVisibleItemPosition()

                        if (lastVisibleItem > totalCount.minus(5) && !isFetching.get()) {
                            fetchMore.invoke()
                        }

                    }
                }

            })

        }
    }
}