package com.demo.app.app.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.demo.app.app.ui.protocols.CellSelectionDelegate

/**
 * Used if you need to deal with multiple ViewHolder in the same Adapter. It allow you to uniformize the way we bind UIModel Item to the ViewHolder
 * @throws NotImplementedError if onClick would be call from a child instance without implementing it. Do not call super of onClick
 */
abstract class BaseViewHolder<T : Any>(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Must be must to attach UIModelItem to the viewHolder
     */
    abstract fun onBind(item: T)

    /**
     * Must be implemented in child instance if you need a click callback on the RecyclerView Cell
     * @see com.citygoo.app.app.ui.protocols.CellSelectionDelegate
     */
    open fun onClick(delegate: CellSelectionDelegate, index: Int) { throw NotImplementedError("BaseViewHolder: onClick called but not implemented") }
}
