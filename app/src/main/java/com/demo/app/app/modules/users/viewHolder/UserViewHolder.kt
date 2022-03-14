package com.demo.app.app.modules.users.viewHolder

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.demo.app.app.ui.protocols.CellSelectionDelegate
import com.demo.app.app.utils.BaseViewHolder
import com.demo.app.presenter.models.UIUserItem
import com.eurosportdemo.app.R
import com.eurosportdemo.app.databinding.ViewHolderUserBinding
import kotlin.math.roundToInt

class UserViewHolder(
    private val binding: ViewHolderUserBinding
) : BaseViewHolder<UIUserItem>(binding) {

    override fun onBind(item: UIUserItem) {
        with(binding) {
            if(item is UIUserItem.Cell) {
                firstNameLabel.text = item.name
                val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
                val requestOptions = RequestOptions().transform(
                    CenterCrop(),
                    RoundedCorners(root.context.resources.getDimension(R.dimen.thumbnail).roundToInt())
                )
                Glide
                    .with(root.context)
                    .load(item.profilePicture)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade(factory))
                    .into(profilePicture)
            }
        }
    }

    override fun onClick(delegate: CellSelectionDelegate, index: Int) {
        binding.root.setOnClickListener {
            delegate.didSelectCellAt(index)
        }
    }
}