package com.gregkluska.datasource.model

import com.google.gson.annotations.SerializedName
import com.gregkluska.domain.model.User

data class UserDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("status")
    val status: String
)

// Mappers
fun User.toUserDto(): UserDto {
    return UserDto(
        id = this.id,
        name = this.name,
        email = this.email,
        gender = this.gender,
        status = this.status
    )
}

fun UserDto.toUser(): User {
    return User(
        id = this.id,
        name = this.name,
        email = this.email,
        gender = this.gender,
        status = this.status
    )
}