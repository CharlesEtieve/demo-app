package com.demo.app.data.models.response

import com.demo.app.data.models.domainMappingProtocols.DomainModelConvertible
import com.demo.app.domain.models.DomainUser
import com.google.gson.annotations.SerializedName

data class UserListResponse(
    @SerializedName("results")
    val results: List<UserResponse>
) : DomainModelConvertible<List<DomainUser>> {

    override fun toDomain(): List<DomainUser> =
        results.map { it.toDomain() }
}