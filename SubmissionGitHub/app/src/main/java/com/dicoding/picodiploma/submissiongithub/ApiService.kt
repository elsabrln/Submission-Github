package com.dicoding.picodiploma.submissiongithub
import retrofit2.Call
import retrofit2.http.*
import com.dicoding.picodiploma.submissiongithub.UserRemote
import com.dicoding.picodiploma.submissiongithub.User
import com.dicoding.picodiploma.submissiongithub.ResponseItem

interface ApiService {
    @GET("search/users")
    fun searchUser(
        @Header("Authorization") token: String,
        @Query("q") username: String
    ): Call<UserRemote>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}")
    fun getUser(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ):Call<ResponseItem>
}