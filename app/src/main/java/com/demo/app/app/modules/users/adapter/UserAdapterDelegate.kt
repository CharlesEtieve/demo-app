package com.demo.app.app.modules.users.adapter

import com.demo.app.presenter.models.UIUserItem

interface UserAdapterDelegate {
    fun didSelectUser(item: UIUserItem)
    fun didDisplayProgress()
}
