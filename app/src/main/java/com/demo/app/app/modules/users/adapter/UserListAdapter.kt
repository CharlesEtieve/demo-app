package com.demo.app.app.modules.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.demo.app.app.modules.users.viewHolder.ProgressViewHolder
import com.demo.app.app.modules.users.viewHolder.UserViewHolder
import com.demo.app.app.ui.protocols.CellSelectionDelegate
import com.demo.app.app.utils.BaseViewHolder
import com.demo.app.presenter.models.UIUserItem
import com.eurosportdemo.app.databinding.ViewHolderProgressBinding
import com.eurosportdemo.app.databinding.ViewHolderUserBinding
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject


class UserListAdapter @Inject constructor(
    diffUtil: UIUserItemDiffUtil
) : ListAdapter<UIUserItem, BaseViewHolder<UIUserItem>>(diffUtil), CellSelectionDelegate {

    val userClickSubject: PublishSubject<Int> = PublishSubject.create()
    val displayProgressSubject: PublishSubject<Unit> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<UIUserItem> {
        return when (viewType) {
            USER_VIEW_TYPE -> {
                val itemBinding = ViewHolderUserBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                UserViewHolder(itemBinding)
            }
            LOAD_MORE_VIEW_TYPE -> {
                val itemBinding = ViewHolderProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ProgressViewHolder(itemBinding)
            }
            else -> throw IllegalStateException("Invalid viewType")
        }
    }

    override fun getItemViewType(position: Int): Int =
        when(getItem(position)) {
            is UIUserItem.Cell -> USER_VIEW_TYPE
            is UIUserItem.Progress -> LOAD_MORE_VIEW_TYPE
        }

    override fun onBindViewHolder(holder: BaseViewHolder<UIUserItem>, position: Int) {
        when (holder) {
            is UserViewHolder -> {
                val model = getItem(position)
                holder.onBind(model)
            }
            is ProgressViewHolder -> {
                displayProgressSubject.onNext(Unit)
            }
        }
    }

    override fun didSelectCellAt(index: Int) {
        val item = getItem(index)
        userClickSubject.onNext(index)
    }

    companion object {
        const val USER_VIEW_TYPE = 0
        const val LOAD_MORE_VIEW_TYPE = 1
    }
}