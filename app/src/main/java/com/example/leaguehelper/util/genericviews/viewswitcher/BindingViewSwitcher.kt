package com.example.leaguehelper.util.genericviews.viewswitcher

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.widget.ViewSwitcher
import me.tatarka.bindingcollectionadapter2.ItemBinding

class BindingViewSwitcher : ViewSwitcher {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    var itemBinding: ItemBinding<Any>? = null
        set(value) {
            if (field == null) {
                field = value
                value?.let { itemBinding ->
                    adapter = ViewSwitcherAdapter(itemBinding)
                }
            }
        }

    var item: Any? = null
        set(value) {
            field = value
            adapter?.item = item
        }

    private var adapter: ViewSwitcherAdapter<Any>? = null
        set(value) {
            field = value
            value?.let { adapter ->
                adapter.attach(
                    onSwitchView = { view ->
                        switchView(view = view)
                    },
                    context = context)
                adapter.item = item
            }
        }

    private fun switchView(view: View?) {
        currentView?.let { currentView ->
            if (view != null) {
                addView(view)
                showNext()
            }

            Handler().post {
                removeView(currentView)
            }
        } ?: {
            if (view != null) {
                addView(view)
            }
        }.invoke()
    }
}