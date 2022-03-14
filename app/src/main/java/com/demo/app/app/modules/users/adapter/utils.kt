package com.demo.app.app.modules.users.adapter

import androidx.recyclerview.widget.DiffUtil
import com.demo.app.presenter.models.UIUserItem
import javax.inject.Inject

class UIUserItemDiffUtil @Inject constructor() : DiffUtil.ItemCallback<UIUserItem>() {

    override fun areItemsTheSame(oldItem: UIUserItem, newItem: UIUserItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: UIUserItem, newItem: UIUserItem): Boolean =
        oldItem == newItem
}