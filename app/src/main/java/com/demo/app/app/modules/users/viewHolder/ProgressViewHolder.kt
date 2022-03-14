package com.demo.app.app.modules.users.viewHolder

import com.demo.app.app.ui.protocols.CellSelectionDelegate
import com.demo.app.app.utils.BaseViewHolder
import com.demo.app.presenter.models.UIUserItem
import com.eurosportdemo.app.databinding.ViewHolderProgressBinding

class ProgressViewHolder(
    binding: ViewHolderProgressBinding
) : BaseViewHolder<UIUserItem>(binding) {

    override fun onBind(item: UIUserItem) {
        //nothing to do
    }

    override fun onClick(delegate: CellSelectionDelegate, index: Int) {
        //nothing to do
    }
}