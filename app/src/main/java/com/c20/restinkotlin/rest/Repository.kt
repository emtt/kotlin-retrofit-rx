package com.c20.restinkotlin.rest

import com.c20.restinkotlin.model.Result

class Repository(val apiService: restClient) {

    fun searchUsers(location: String, language: String): io.reactivex.Observable<Result> {
        return apiService.search(query = "location:$location language:$language")
    }

}