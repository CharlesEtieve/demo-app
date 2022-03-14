package com.demo.app.data.models.response

import com.demo.app.data.models.domainMappingProtocols.DomainModelConvertible
import com.google.gson.annotations.SerializedName
import java.util.*

data class IdResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("value")
    val value: String?
) : DomainModelConvertible<String> {

    override fun toDomain(): String =
        if(name.isNullOrEmpty() || value.isNullOrEmpty()) {
            //I use this method because we cannot autogenerate String id with Room
            UUID.randomUUID().toString()
        } else {
            "$name$value"
        }
}
