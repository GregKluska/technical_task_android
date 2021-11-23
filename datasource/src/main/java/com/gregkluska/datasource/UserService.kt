package com.gregkluska.datasource

import com.gregkluska.datasource.model.UserDto
import com.gregkluska.datasource.util.GenericResponse
import retrofit2.Response
import retrofit2.http.*

interface UserService {


    @GET("users")
    suspend fun getUsers(@Query(value = "page") page: Int? = null): Response<GenericResponse<List<UserDto>>>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Long): Response<String>

    @FormUrlEncoded
    @POST("users")
    suspend fun addUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("gender") gender: String,
        @Field("status") status: String
    ): Response<GenericResponse<UserDto>>

}