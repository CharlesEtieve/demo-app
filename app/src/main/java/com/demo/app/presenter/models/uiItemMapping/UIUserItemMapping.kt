package com.demo.app.presenter.models.uiItemMapping

import com.demo.app.domain.models.DomainUser
import com.demo.app.domain.models.DomainUserPage
import com.demo.app.presenter.models.UIUserItem

fun DomainUser.toUIItem(): UIUserItem =
    UIUserItem.Cell(
        id = id,
        name = name,
        profilePicture = profilePicture
    )

fun List<DomainUserPage>.toUIItem(): List<UIUserItem> =
    ArrayList<UIUserItem>().also {
        for(userPage in this) {
            it.addAll(
                userPage.userList.map { user ->
                    user.toUIItem()
                }
            )
        }
        if(isNotEmpty() && last().canRefresh) {
            it.add(UIUserItem.Progress)
        }
    }