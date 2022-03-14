package com.demo.app.data.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.app.data.models.domainMappingProtocols.DomainModelConvertible
import com.demo.app.domain.models.DomainUser

@Entity
data class UserEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "profilePicture")
    val profilePicture: String?
) : DomainModelConvertible<DomainUser> {

    override fun toDomain(): DomainUser =
        DomainUser(
            id = id,
            name = name,
            profilePicture = profilePicture
        )
}