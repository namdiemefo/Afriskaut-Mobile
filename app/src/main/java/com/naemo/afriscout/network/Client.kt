package com.naemo.afriscout.network

import android.content.Context
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.naemo.afriscout.api.models.forgot.ForgotRequest
import com.naemo.afriscout.api.models.forgot.ForgotResponse
import com.naemo.afriscout.api.models.login.LoginRequest
import com.naemo.afriscout.api.models.login.LoginResponse
import com.naemo.afriscout.api.models.player.follow.FollowResponse
import com.naemo.afriscout.api.models.player.profile.ProfileRequest
import com.naemo.afriscout.api.models.player.profile.ProfileResponse
import com.naemo.afriscout.api.models.profile.ProfileImageResponse
import com.naemo.afriscout.api.models.register.RegisterRequest
import com.naemo.afriscout.api.models.register.RegisterResponse
import com.naemo.afriscout.api.models.search.SearchRequest
import com.naemo.afriscout.api.models.search.SearchResponse
import com.naemo.afriscout.db.local.room.profilepicture.ProfilePic
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Client {
    private var PROD_BASE_URL = "http://18.216.71.154:5000/"
    private var LOCAL_BASE_URL = "http://192.168.1.3:5000/"
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
            .baseUrl(LOCAL_BASE_URL)
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
    fun follow(@Header("Authorization") token: String, @Body profileRequest: ProfileRequest): Call<FollowResponse>

}