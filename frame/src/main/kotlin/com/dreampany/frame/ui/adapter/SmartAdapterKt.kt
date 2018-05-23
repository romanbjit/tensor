package com.dreampany.frame.ui.adapter

import android.content.Context
import android.view.View
import com.dreampany.frame.ui.model.BaseItem
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.databinding.BindingFlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import java.util.Comparator


/**
 * Created by Hawladar Roman on 5/22/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */
open class SmartAdapterKt<T : BaseItem<*, *>>(listener: Any) : BindingFlexibleAdapter<T>(listener) {
    var clickListener: View.OnClickListener? = null
        private set
    var longClickListener: View.OnLongClickListener? = null
        private set

    init {
        if (listener is View.OnClickListener) {
            clickListener = listener
        }
        if (listener is View.OnLongClickListener) {
            longClickListener = listener
        }
    }

    override fun addItem(item: T): Boolean {
        if (contains(item)) {
            updateItem(item)
            return true
        } else {
            return super.addItem(item)
        }
    }

    override fun addItem(position: Int, item: T): Boolean {
        if (contains(item)) {
            updateItem(item)
            return true
        } else {
            return super.addItem(position, item)
        }
    }

    fun addItem(item: T, comparator: Comparator<IFlexible<*>>): Boolean {
        if (contains(item)) {
            updateItem(item)
            return true
        } else {
            return super.addItem(calculatePositionFor(item, comparator), item)
        }
    }

    override fun toggleSelection(position: Int) {
        super.toggleSelection(position)
        notifyItemChanged(position)
    }

    fun getPosition(item: T): Int {
        return getGlobalPositionOf(item)
    }

    fun isAnySelected(): Boolean {
        return selectedItemCount > 0
    }

    fun addSelection(item: T, selected: Boolean) {
        val position = getGlobalPositionOf(item)
        if (position >= 0) {
            if (selected) {
                addSelection(position)
            } else {
                removeSelection(position)
            }
            notifyItemChanged(position)
        }
    }

    fun selectAll() {
        super.selectAll()
        notifyDataSetChanged()
    }

    override fun clearSelection() {
        super.clearSelection()
        notifyDataSetChanged()
    }

    fun isSelected(item: T): Boolean {
        return isSelected(getGlobalPositionOf(item))
    }

    fun getSelectedItems(): List<T>? {
        if (selectedItemCount > 0) {
            val positions = selectedPositions
            val items = ArrayList<T>(positions.size)
            for (position in positions) {
                val item: T? = getItem(position)
                if (item != null) {
                    items.add(item)
                }
            }
            return items
        }
        return null
    }

    class SmartViewHolder protected constructor(view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter) {

        val context: Context
            get() = itemView.context
    }
}