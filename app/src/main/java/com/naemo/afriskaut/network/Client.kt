package com.naemo.afriskaut.network

import android.content.Context
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.naemo.afriskaut.api.models.forgot.ForgotRequest
import com.naemo.afriskaut.api.models.forgot.ForgotResponse
import com.naemo.afriskaut.api.models.login.LoginRequest
import com.naemo.afriskaut.api.models.login.LoginResponse
import com.naemo.afriskaut.api.models.player.follow.*
import com.naemo.afriskaut.api.models.profile.ProfileImageResponse
import com.naemo.afriskaut.api.models.profile.ProfilePicResponse
import com.naemo.afriskaut.api.models.register.RegisterRequest
import com.naemo.afriskaut.api.models.register.RegisterResponse
import com.naemo.afriskaut.api.models.report.*
import com.naemo.afriskaut.api.models.search.ScoutPlayerRequest
import com.naemo.afriskaut.api.models.search.ScoutPlayerResponse
import com.naemo.afriskaut.api.models.search.SearchPlayerRequest
import com.naemo.afriskaut.api.models.search.SearchPlayerResponse
import com.naemo.afriskaut.api.models.suggestion.SuggestionRequest
import com.naemo.afriskaut.api.models.suggestion.SuggestionResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Client {
    private var PROD_BASE_URL = "https://afriskaut-api.herokuapp.com/"
   // private var LOCAL_BASE_URL = "http://172.20.10.7:5000/"
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
    fun retrieveImage(@Header("Authorization")token: String): Call<ProfilePicResponse>

    @POST("search/player")
    fun search(@Header("Authorization") token: String, @Body searchPlayerRequest: SearchPlayerRequest): Call<SearchPlayerResponse>

    @POST("search/scout")
    fun scout(@Header("Authorization") token: String, @Body scoutPlayerRequest: ScoutPlayerRequest): Call<ScoutPlayerResponse>

    @POST("users/follow")
    fun follow(@Header("Authorization") token: String, @Body followRequest: FollowRequest): Call<FollowResponse>

    @POST("users/following")
    fun following(@Header("Authorization") token: String): Call<FollowingResponse>

    @POST("users/unfollow")
    fun unfollow(@Header("Authorization") token: String, @Body unfollowRequest: UnfollowRequest): Call<UnFollowResponse>

    @POST("users/suggestions")
    fun suggestion(@Header("Authorization") token: String, @Body suggestionRequest: SuggestionRequest): Call<SuggestionResponse>

    @POST("users/create-match-report")
    fun createReport(@Header("Authorization") token: String, @Body createReportRequest: CreateReportRequest): Call<CreateReportResponse>

    @POST("users/view-match-report")
    fun viewReport(@Header("Authorization") token: String, @Body viewReportRequest: ViewReportRequest): Call<ViewReportResponse>

    @POST("users/delete-match-report")
    fun deleteReport(@Header("Authorization") token: String, @Body deleteReportRequest: DeleteReportRequest): Call<CreateReportResponse>

    @POST("users/view-all-report")
    fun viewAllReport(@Header("Authorization") token: String): Call<ViewReportResponse>



}