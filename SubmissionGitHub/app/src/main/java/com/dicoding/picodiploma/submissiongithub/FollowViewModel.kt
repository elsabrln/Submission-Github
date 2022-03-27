package com.dicoding.picodiploma.submissiongithub

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.submissiongithub.User
import com.dicoding.picodiploma.submissiongithub.Repository
import com.dicoding.picodiploma.submissiongithub.Resource

class FollowViewModel : ViewModel (){
    private val repository: Repository = Repository()

    fun getFollowers(username: String): LiveData<Resource<ArrayList<User>>> = repository.getFollowers(username)

    fun getFollowing(username: String): LiveData<Resource<ArrayList<User>>> = repository.getFollowing(username)
}