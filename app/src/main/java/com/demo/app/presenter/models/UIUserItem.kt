package com.demo.app.presenter.models

sealed class UIUserItem {
    data class Cell(
        val id: String,
        val name: String,
        val profilePicture: String?
    ) : UIUserItem()
    object Progress : UIUserItem()
}