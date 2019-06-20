package com.example.leaguehelper.util.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.leaguehelper.util.ImageLoader

class CustomBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImageUrl(imageView: ImageView, url: String?) {
            url?.let {
                ImageLoader.loadImage(imageView.context, url, imageView)
            }
        }

        @JvmStatic
        @BindingAdapter("pagination") // add canFetchMore bool og isFetchingBool from VM
        fun setLoadMoreCallBack(recyclerView: RecyclerView, fetchMore: () -> Unit) {

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    (recyclerView.layoutManager as? LinearLayoutManager)?.let { lm ->

                        val totalCount = lm.itemCount
                        val lastVisibleItem = lm.findLastVisibleItemPosition()

                        if (lastVisibleItem < totalCount.minus(5) && !isFetching && canFetchMore) {
                            fetchMore.invoke()
                        }

                    }
                }

            })

        }
    }
}