package com.udemy.findrepo

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("search/repositories?")

    fun searchRepos(@Query("q") searchTerm: String): Call<GitHubSearchResult>
}

class GitHubSearchResult(val items: MutableList<Repo>)
class Repo(val full_name: String, val owner: GitHubUser, val html_url: String)
class GitHubUser(val avatar_url: String)

class GitHubRetriever {
    val service: GitHubService

    init {
        val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        service = retrofit.create(GitHubService::class.java)
    }

    fun searchRepos(callback: Callback<GitHubSearchResult>, searchTerm: String) {
        var searchT = searchTerm
        if (searchT.isBlank()) {
            searchT = "kotlin"
        }

        val call = service.searchRepos(searchT)
        call.enqueue(callback)
    }
}