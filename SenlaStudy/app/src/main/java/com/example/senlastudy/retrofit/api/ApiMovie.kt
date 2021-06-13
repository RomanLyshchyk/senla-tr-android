package com.example.senlastudy.retrofit.api

import com.example.senlastudy.retrofit.pojo.MovieListResponse
import com.example.senlastudy.retrofit.pojo.MovieUpcomingListResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMovie {

    @GET("movie/popular")
    fun getPopularMovie(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<MovieListResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovie(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<MovieListResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovie(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<MovieUpcomingListResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovie(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<MovieUpcomingListResponse>

    @GET("movie/{movie_id}")
    fun getMovie(
        @Path("movie_id")
        @Query("language") language: String
    ): Observable<MovieUpcomingListResponse>
}
