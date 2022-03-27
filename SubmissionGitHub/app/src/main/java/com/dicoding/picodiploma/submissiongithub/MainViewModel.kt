package com.dicoding.picodiploma.submissiongithub

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){
    private val repository: Repository = Repository()

    fun setSearchResult(username: String): LiveData<Resource<ArrayList<User>>> = repository.loadSearchResult(username)
}