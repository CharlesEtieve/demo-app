package com.demo.app

import com.demo.app.data.models.entities.UserEntity
import com.demo.app.data.models.response.*

class DataFixtures {
    object UserListResponseUtils {
        fun create(): UserListResponse =
            UserListResponse(
                results = listOf(UserResponseUtils.create())
            )
    }

    object UserResponseUtils {
        fun create(): UserResponse =
            UserResponse(
                name = NameResponseUtils.create(),
                id = IdResponseUtils.create(),
                profilePicture = ProfilePictureResponseUtils.create()
            )
    }

    object NameResponseUtils {
        fun create(): NameResponse =
            NameResponse(
                title = FixtureConstants.User.title,
                firstName = FixtureConstants.User.firstName,
                lastName = FixtureConstants.User.lastName
            )
    }

    object IdResponseUtils {
        fun create(): IdResponse =
            IdResponse(
                name = FixtureConstants.User.nameId,
                value = FixtureConstants.User.valueId,
            )
    }

    object ProfilePictureResponseUtils {
        fun create(): ProfilePictureResponse =
            ProfilePictureResponse(
                large = FixtureConstants.User.profilePictureLarge,
                medium = FixtureConstants.User.profilePictureMedium,
                thumbnail = FixtureConstants.User.profilePictureThumbnail
            )
    }

    object UserEntityUtils {
        fun create(): UserEntity =
            UserEntity(
                id = FixtureConstants.User.id,
                name = FixtureConstants.User.name,
                profilePicture = FixtureConstants.User.profilePictureThumbnail
            )
    }
}