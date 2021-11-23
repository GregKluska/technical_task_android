package com.gregkluska.datasource

import com.gregkluska.datasource.model.Meta
import com.gregkluska.datasource.model.Pagination
import com.gregkluska.datasource.model.UserDto
import com.gregkluska.datasource.util.GenericResponse
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

class UserServiceFake(
    private val responseType: ResponseType
) : UserService {

    private val db = users.toMutableList()

    override suspend fun getUsers(page: Int?): Response<GenericResponse<List<UserDto>>> {
        when (responseType) {
            ResponseType.Good -> {
                if (page == 2) {
                    // Only last page shows users
                    return Response.success(
                        200,
                        GenericResponse(
                            meta = Meta(Pagination(2)),
                            data = db
                        )
                    )
                } else {
                    return Response.success(
                        200,
                        GenericResponse(
                            meta = Meta(Pagination(2)),
                            data = listOf()
                        )
                    )
                }
            }
            ResponseType.Error -> {
                return Response.error(
                    404,
                    ResponseBody.create(null, "")
                )
            }
            ResponseType.NetworkError -> {
                throw IOException()
            }
            ResponseType.AuthError -> {
                return Response.error(
                    401,
                    ResponseBody.create(null, "")
                )
            }
        }
    }

    override suspend fun deleteUser(id: Long): Response<String> {
        when (responseType) {
            ResponseType.Good -> {
                val res = db.removeAll { it.id == id }
                return if (res) {
                    Response.success(
                        204,
                        ""
                    )
                } else {
                    Response.error(
                        404,
                        ResponseBody.create(null, "")
                    )
                }

            }
            ResponseType.Error -> {
                return Response.error(
                    404,
                    ResponseBody.create(null, "")
                )
            }
            ResponseType.NetworkError -> {
                throw IOException()
            }
            ResponseType.AuthError -> {
                return Response.error(
                    401,
                    ResponseBody.create(null, "")
                )
            }
        }
    }

    override suspend fun addUser(
        name: String,
        email: String,
        gender: String,
        status: String
    ): Response<GenericResponse<UserDto>> {
        when (responseType) {
            ResponseType.Good -> {
                // Get next id
                val nextId = db.maxByOrNull { it.id }?.id?.inc() ?: 1
                // check if the email already exists
                val emailExists = db.find { it.email == email } != null

                val user = UserDto(
                    id = nextId,
                    name = name,
                    email = email,
                    gender = gender,
                    status = "active",
                )

                val res = if (!emailExists) db.add(user) else false

                return if (res) {
                    Response.success(
                        201,
                        GenericResponse(
                            meta = null,
                            data = user
                        )
                    )
                } else {
                    Response.error(
                        422,
                        ResponseBody.create(null, "")
                    )
                }

            }
            ResponseType.Error -> {
                return Response.error(
                    404,
                    ResponseBody.create(null, "")
                )
            }
            ResponseType.NetworkError -> {
                throw IOException()
            }
            ResponseType.AuthError -> {
                return Response.error(
                    401,
                    ResponseBody.create(null, "")
                )
            }
        }
    }
}