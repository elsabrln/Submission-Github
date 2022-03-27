package com.dicoding.picodiploma.submissiongithub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.BuildConfig
import com.dicoding.picodiploma.submissiongithub.ResponseItem
import com.dicoding.picodiploma.submissiongithub.User
import retrofit2.Callback
import com.dicoding.picodiploma.submissiongithub.Resource
import com.dicoding.picodiploma.submissiongithub.UserRemote
import retrofit2.Call
import retrofit2.Response

class Repository {
    private val apiClient = ApiConfig.getApiService()

    fun loadSearchResult(username: String): LiveData<Resource<ArrayList<User>>> {
        val users = MutableLiveData<Resource<ArrayList<User>>>()
        apiClient.searchUser(com.dicoding.picodiploma.submissiongithub.BuildConfig.API_KEY_GITHUB, username).enqueue(object : Callback<UserRemote>{
            override fun onResponse(call: Call<UserRemote>, response: Response<UserRemote>) {
                users.value = Resource.loading()
                if (response.isSuccessful) {
                    response.body().let {
                        users.value = Resource.success(response.body()?.items)
                    }
                }
            }

            override fun onFailure(call: Call<UserRemote>, t: Throwable) {
                users.value = Resource.error(t.message.toString())
            }
        })
        return users
    }

    fun getFollowers(username: String): LiveData<Resource<ArrayList<User>>> {
        val users = MutableLiveData<Resource<ArrayList<User>>>()
        apiClient.getFollowers(com.dicoding.picodiploma.submissiongithub.BuildConfig.API_KEY_GITHUB, username).enqueue(object : Callback<ArrayList<User>>{
            override fun onResponse(call:Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                users.value = Resource.loading()
                if (response.isSuccessful){
                    response.body().let{
                        users.value = Resource.success(response.body())
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                users.value = Resource.error(t.message.toString())
            }
        })
        return users

    }

    fun getFollowing(username: String): LiveData<Resource<ArrayList<User>>>{
        val users = MutableLiveData<Resource<ArrayList<User>>>()
        apiClient.getFollowing(com.dicoding.picodiploma.submissiongithub.BuildConfig.API_KEY_GITHUB, username).enqueue(object : Callback<ArrayList<User>>{
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                users.value = Resource.loading()
                if (response.isSuccessful){
                    response.body().let {
                        users.value = Resource.success(response.body())
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                users.value = Resource.error(t.message.toString())
            }
        })
        return users
    }

    fun getUser(username: String): LiveData<Resource<ResponseItem>> {
        val user = MutableLiveData<Resource<ResponseItem>>()
        apiClient.getUser(com.dicoding.picodiploma.submissiongithub.BuildConfig.API_KEY_GITHUB, username).enqueue(object : Callback<ResponseItem>{
            override fun onResponse(call: Call<ResponseItem>, response: Response<ResponseItem>) {
                user.value = Resource.loading()
                if (response.isSuccessful){
                    response.body().let {
                        user.value = Resource.success(response.body())
                    }
                }
            }


            override fun onFailure(call: Call<ResponseItem>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        return user

        }

    }





