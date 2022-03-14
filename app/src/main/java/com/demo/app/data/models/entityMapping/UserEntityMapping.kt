package com.demo.app.data.models.entityMapping

import com.demo.app.data.models.entities.UserEntity
import com.demo.app.domain.models.DomainUser

fun DomainUser.toEntity(): UserEntity =
    UserEntity(
        id = id,
        name = name,
        profilePicture = profilePicture
    )