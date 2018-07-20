package com.c20.restinkotlin.rest


import com.c20.restinkotlin.model.Result
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface restClient {

    @GET("search/users")
    fun search(@Query("q") query: String,
               @Query("page") page: Int,
               @Query("per_page") perPage: Int): Observable<Result>

    @GET("search/users")
    fun search(@Query("q") query: String): Observable<Result>

    /**
     * Companion object to create the RestClient
     */
    companion object Factory {
        fun create(): restClient {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .build()

            return retrofit.create(restClient::class.java)
        }
    }
}