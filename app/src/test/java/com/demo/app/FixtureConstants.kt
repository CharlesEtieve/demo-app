package com.demo.app

class FixtureConstants {
    class User {
        companion object {
            const val nameId = "SSN"
            const val valueId = "198-59-5353"
            const val id = "$nameId$valueId"
            const val title = "M"
            const val firstName = "Charles"
            const val lastName = "Etieve"
            const val name = "$title $firstName $lastName"
            const val profilePictureThumbnail = "https://randomuser.me/api/portraits/thumb/men/72.jpg"
            const val profilePictureMedium = "https://randomuser.me/api/portraits/med/men/72.jpg"
            const val profilePictureLarge = "https://randomuser.me/api/portraits/men/72.jpg"
        }
    }
}