package com.naemo.afriscout.network

import android.content.Context
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.naemo.afriscout.api.models.LoginRequest
import com.naemo.afriscout.api.models.LoginResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Client {
    private var BASE_URL = "http://18.216.71.154:5000/"
    private var service: Service
    var context: Context? = null
        @Inject set

    init {
        this.context = context

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

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
            .baseUrl(BASE_URL)
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
}