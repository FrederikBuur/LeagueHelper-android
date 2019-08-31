package com.example.leaguehelper.util.genericviews.viewswitcher

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.content.Context
import me.tatarka.bindingcollectionadapter2.ItemBinding

class ViewSwitcherAdapter<T>(private val itemBinding: ItemBinding<T>) {

    private var currentBinding: ViewDataBinding? = null
    private var compareString: String? = null

    fun attach(context: Context, onSwitchView: (view: View?) -> (Unit)) {
        this.context = context
        this.onSwitchView = onSwitchView

        trySwitchView()
    }

    var item: T? = null
        set(value) {
            synchronized(this) {
                field = value
                trySwitchView()
            }
        }

    private var context: Context? = null

    private var onSwitchView: ((view: View?) -> (Unit))? = null

    private fun trySwitchView() {
        context?.let { context ->
            onSwitchView?.let { onSwitchView ->
                item?.let { item ->
                    switchView(
                        itemBinding = itemBinding,
                        item = item,
                        context = context,
                        onSwitchView = onSwitchView
                    )
                } ?: clearView(onSwitchView)
            }
        }
    }

    private fun clearView(onSwitchView: (view: View?) -> Unit) {
        itemBinding.set(0, 0)
        currentBinding = null
        onSwitchView.invoke(null)
    }

    private fun switchView(
        itemBinding: ItemBinding<T>,
        item: T,
        context: Context,
        onSwitchView: (view: View?) -> Unit
    ) {
        val currentlayoutRes = itemBinding.layoutRes()
        itemBinding.onItemBind(0, item)

        var inflate = currentlayoutRes != itemBinding.layoutRes()
        var bind = true
        if (!inflate) {
            if (item.toString() != compareString) {
                inflate = true
                bind = true
            }
        }

        if (inflate) {
            currentBinding = DataBindingUtil.inflate(LayoutInflater.from(context), itemBinding.layoutRes(), null, false)
        }

        currentBinding?.let { binding ->
            if (bind) {
                binding.setVariable(itemBinding.variableId(), item)
                binding.executePendingBindings()
            }

            if (inflate) {
                onSwitchView.invoke(binding.root)
            }
        }

        compareString = item.toString()
    }
}