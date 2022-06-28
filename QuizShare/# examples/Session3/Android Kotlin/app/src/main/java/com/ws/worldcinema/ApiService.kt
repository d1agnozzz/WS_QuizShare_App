package com.ws.worldcinema

import com.ws.worldcinema.model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ApiService {
    @POST("auth/login")
    fun signIn(@Body userSignIn: UserSignIn?): Call<SignInResponse?>?

    @GET("movies/cover")
    fun getCover(@Header("Authorization") authHeader: String?): Call<Cover?>

    @GET("movies")
    fun getMovies(@Query("filter") filter: String?): Call<ArrayList<Movie>?>

    @GET("movies/{movieId}/episodes")
    fun getMovieEpisodes(@Path("movieId") movieId: Int): Call<ArrayList<Episode>?>

    @GET("user/chats")
    fun getChats(@Header("Authorization") authHeader: String?): Call<ArrayList<Chat>?>

    @GET("chats/{chatId}/messages")
    fun getMessages(@Path("chatId") chatId: Int, @Header("Authorization") authHeader: String?): Call<ArrayList<Message>?>

    @POST("chats/{chatId}/messages")
    fun sendMessage(@Body messageForm: MessageForm?, @Path("chatId") chatId: Int, @Header("Authorization") authHeader: String?): Call<ArrayList<Message>?>

    @GET("user")
    fun getProfile(@Header("Authorization") authHeader: String?): Call<User?>

    @GET("usermovies")
    fun getUserMovies(@Header("Authorization") authHeader: String?, @Query("filter") filter: String?): Call<ArrayList<Movie>?>

    @Multipart
    @POST("user/avatar")
    fun editUser(@PartMap map: Map<String?, RequestBody?>?): Call<ArrayList<User>?>

    @GET("movies/{movieId}")
    fun getMovie(@Path("movieId") movieId: Int, @Header("Authorization") authHeader: String?): Call<Movie?>
}