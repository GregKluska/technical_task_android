package com.gregkluska.domain

import com.gregkluska.domain.model.User

val users = listOf(
    User(1, "John Smith", "john.smith@email.com", "male", "active"),
    User(2, "David Baker", "david.baker@email.com", "male", "active"),
    User(3, "Denis Ball", "denis.ball@email.com", "male", "active"),
    User(4, "Kun Li", "kun.li@email.com", "male", "active"),
    User(5, "Karen Noel", "karen.noel@email.com", "female", "active"),
    User(6, "Sarah Lovett", "sarah.lovett@email.com", "female", "active"),
    User(7, "Patrick Ellis", "patrick.ellis@email.com", "male", "active"),
    User(8, "Jeffrey Smith", "jeffrey.smith@email.com", "male", "active"),
    User(9, "Scott Martin", "scott.martin@email.com", "male", "active"),
    User(10, "Carolyn Ford", "carolyn.ford@email.com", "female", "active"),
)

enum class ResponseType { Good, Error }

const val EXCEPTION_USER_LIST = "UserList"
const val EXCEPTION_USER_ADD = "UserAdd"
const val EXCEPTION_USER_DELETE = "UserDelete"