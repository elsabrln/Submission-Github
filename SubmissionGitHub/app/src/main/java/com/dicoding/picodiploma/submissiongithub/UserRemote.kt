package com.dicoding.picodiploma.submissiongithub
import com.google.gson.annotations.SerializedName
import com.dicoding.picodiploma.submissiongithub.User

data class UserRemote (
    @SerializedName("items")
    var items: ArrayList<User>
        )