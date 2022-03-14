package com.demo.app.data.models.response

import com.demo.app.data.models.domainMappingProtocols.DomainModelConvertible
import com.google.gson.annotations.SerializedName

data class ProfilePictureResponse(
    @SerializedName("large")
    val large: String?,
    @SerializedName("medium")
    val medium: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?
) : DomainModelConvertible<String?> {

    override fun toDomain(): String? =
        thumbnail
}