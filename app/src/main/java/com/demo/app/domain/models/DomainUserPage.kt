package com.demo.app.domain.models

data class DomainUserPage(
    val userList: List<DomainUser>,
    val canRefresh: Boolean
)