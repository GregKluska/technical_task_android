package com.gregkluska.datasource

import com.gregkluska.datasource.model.UserDto

val users = listOf(
    UserDto(1, "John Smith", "john.smith@email.com", "male", "active"),
    UserDto(2, "David Baker", "david.baker@email.com", "male", "active"),
    UserDto(3, "Denis Ball", "denis.ball@email.com", "male", "active"),
    UserDto(4, "Kun Li", "kun.li@email.com", "male", "active"),
    UserDto(5, "Karen Noel", "karen.noel@email.com", "female", "active"),
    UserDto(6, "Sarah Lovett", "sarah.lovett@email.com", "female", "active"),
    UserDto(7, "Patrick Ellis", "patrick.ellis@email.com", "male", "active"),
    UserDto(8, "Jeffrey Smith", "jeffrey.smith@email.com", "male", "active"),
    UserDto(9, "Scott Martin", "scott.martin@email.com", "male", "active"),
    UserDto(10, "Carolyn Ford", "carolyn.ford@email.com", "female", "active"),
)

enum class ResponseType { Good, Error, NetworkError, AuthError }

const val LAST_PAGE = 2