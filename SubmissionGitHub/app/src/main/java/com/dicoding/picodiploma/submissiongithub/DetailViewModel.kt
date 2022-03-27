package com.dicoding.picodiploma.submissiongithub

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class DetailViewModel: ViewModel() {
    private val repository: Repository = Repository()

    fun getUser(username: String) : LiveData<Resource<ResponseItem>> = repository.getUser(username)
}