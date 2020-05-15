package com.naemo.afriskaut.network

import android.content.Context
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.naemo.afriskaut.api.models.forgot.ForgotRequest
import com.naemo.afriskaut.api.models.forgot.ForgotResponse
import com.naemo.afriskaut.api.models.login.LoginRequest
import com.naemo.afriskaut.api.models.login.LoginResponse
import com.naemo.afriskaut.api.models.player.follow.FollowResponse
import com.naemo.afriskaut.api.models.player.follow.FollowingResponse
import com.naemo.afriskaut.api.models.player.follow.UnfollowResponse
import com.naemo.afriskaut.api.models.player.league.LeagueNameRequest
import com.naemo.afriskaut.api.models.player.league.LeagueNameResponse
import com.naemo.afriskaut.api.models.player.profile.ProfileRequest
import com.naemo.afriskaut.api.models.player.profile.ProfileResponse
import com.naemo.afriskaut.api.models.player.season.SeasonNameRequest
import com.naemo.afriskaut.api.models.player.season.SeasonNameResponse
import com.naemo.afriskaut.api.models.player.stats.PlayerStatsRequest
import com.naemo.afriskaut.api.models.player.stats.PlayerStatsResponse
import com.naemo.afriskaut.api.models.player.team.TeamNameRequest
import com.naemo.afriskaut.api.models.player.team.TeamNameResponse
import com.naemo.afriskaut.api.models.profile.ProfileImageResponse
import com.naemo.afriskaut.api.models.register.RegisterRequest
import com.naemo.afriskaut.api.models.register.RegisterResponse
import com.naemo.afriskaut.api.models.search.SearchRequest
import com.naemo.afriskaut.api.models.search.SearchResponse
import com.naemo.afriskaut.db.local.room.profilepicture.ProfilePic
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Client {
    private var PROD_BASE_URL = "https://whispering-castle-67024.herokuapp.com/"
    private var LOCAL_BASE_URL = "http://172.20.10.7:5000/"
    private var service: Service
    var context: Context? = null
        @Inject set

    init {
        this.context = context

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .method(originalRequest.method(), originalRequest.body())
                    .build()

                chain.proceed(newRequest)
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(PROD_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(Service::class.java)
    }

    fun getApi(): Service {
        return service
    }
}

interface Service {
    @POST("users/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("users/register")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("users/reset-password")
    fun reset(@Body forgotRequest: ForgotRequest): Call<ForgotResponse>

    @Multipart
    @POST("users/profileimage")
    fun upload(@Header("Authorization")token: String, @Part image: MultipartBody.Part): Call<ProfileImageResponse>

    @POST("users/getprofileimage")
    fun retrieveImage(@Header("Authorization")token: String): Call<ProfilePic>

    @POST("users/search")
    fun search(@Header("Authorization") token: String, @Body searchRequest: SearchRequest): Call<SearchResponse>

    @POST("users/playerprofile")
    fun profile(@Header("Authorization") token: String, @Body profileRequest: ProfileRequest): Call<ProfileResponse>

    @POST("users/follow")
    fun follow(@Header("Authorization") token: String, @Body followRequest: ProfileRequest): Call<FollowResponse>

    @POST("users/following")
    fun following(@Header("Authorization") token: String): Call<FollowingResponse>

    @POST("users/unfollow")
    fun unfollow(@Header("Authorization") token: String, @Body unfollowRequest: ProfileRequest): Call<UnfollowResponse>

    @POST("users/playerstats")
    fun stats(@Header("Authorization") token: String, @Body playerStatsRequest: PlayerStatsRequest): Call<PlayerStatsResponse>

    @POST("users/team")
    fun team(@Header("Authorization") token: String, @Body teamNameRequest: TeamNameRequest): Call<TeamNameResponse>

    @POST("users/season")
    fun season(@Header("Authorization") token: String, @Body seasonNameRequest: SeasonNameRequest): Call<SeasonNameResponse>

    @POST("users/league")
    fun league(@Header("Authorization") token: String, @Body leagueNameRequest: LeagueNameRequest): Call<LeagueNameResponse>

}