package com.demo.app

import com.demo.app.domain.models.DomainUser
import com.demo.app.domain.models.DomainUserPage

class DomainFixtures {

    object DomainUserUtils {
        fun create(): DomainUser =
            DomainUser(
                id = FixtureConstants.User.id,
                name = FixtureConstants.User.name,
                profilePicture = FixtureConstants.User.profilePictureThumbnail
            )
    }

    object DomainUserPageUtils {
        fun create(): DomainUserPage =
            DomainUserPage(
                userList = listOf(DomainUserUtils.create()),
                canRefresh = true
            )
    }
}