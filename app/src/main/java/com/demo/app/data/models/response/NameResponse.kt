package com.demo.app.data.models.response

import com.demo.app.data.models.domainMappingProtocols.DomainModelConvertible
import com.google.gson.annotations.SerializedName

data class NameResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("first")
    val firstName: String,
    @SerializedName("last")
    val lastName: String
) : DomainModelConvertible<String> {

    override fun toDomain(): String =
        "$title $firstName $lastName"

}