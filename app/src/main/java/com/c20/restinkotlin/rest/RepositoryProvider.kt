package com.c20.restinkotlin.rest


//An object declaration in Kotlin is the way a singleton is made in Kotlin.
//Singletons in Kotlin is as simple as declaring an object and qualifying it with a name. For example:
//Usage in_ Retrofit.kt Line:19

object RepositoryProvider {
    fun provideSearchRepository(): Repository {
        return Repository(restClient.create())
    }
}