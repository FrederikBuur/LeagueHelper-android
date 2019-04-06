package com.example.leaguehelper.pages.champions

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.leaguehelper.models.staticdata.champion.Champion
import com.turingtechnologies.materialscrollbar.INameableAdapter

class ChampionsAdapter(var context: Context) : INameableAdapter, ListAdapter<Champion, ChampionsAdapter.ChampionViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChampionViewHolder {
        return ChampionViewHolder((ChampionsChampionView(context)))
    }

    override fun onBindViewHolder(holder: ChampionViewHolder, position: Int) {
        val itemView = holder.itemView
        val champion = getItem(position)

        when(itemView) {
            is ChampionsChampionView -> {itemView.setupView(champion)}
            else -> {
                Log.d(TAG, "Unknown itemView: $itemView")
            }
        }
    }

    override fun getCharacterForElement(element: Int): Char {
        if (itemCount < 1) return '0'
        val e = if (element < 0) 0 else element
        var c: Char = getItem(e).name.substring(0, 1).toCharArray().first()
        if (c.isDigit()) {
           c = '#'
        }
        return c
    }

    inner class ChampionViewHolder(container: FrameLayout) : RecyclerView.ViewHolder(container) {
        init {
        }
    }

    companion object {
        const val TAG = "ChampionAdapter"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Champion>() {
            override fun areItemsTheSame(oldItem: Champion, newItem: Champion): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Champion, newItem: Champion): Boolean {
                return oldItem.title == newItem.title &&
                        oldItem.name == newItem.name &&
                        oldItem.blurb == newItem.blurb
            }
        }
    }
}