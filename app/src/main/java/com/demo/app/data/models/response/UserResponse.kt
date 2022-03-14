package com.demo.app.data.models.response

import com.demo.app.data.models.domainMappingProtocols.DomainModelConvertible
import com.demo.app.domain.models.DomainUser
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("name")
    val name: NameResponse,
    @SerializedName("id")
    val id: IdResponse,
    @SerializedName("picture")
    val profilePicture: ProfilePictureResponse
) : DomainModelConvertible<DomainUser> {

    override fun toDomain(): DomainUser =
        DomainUser(
            id = id.toDomain(),
            name = name.toDomain(),
            profilePicture = profilePicture.toDomain()
        )
}
