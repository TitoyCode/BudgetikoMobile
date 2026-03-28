package com.budgetiko.budgetikomobile.data.network

import com.google.gson.Gson
import java.util.concurrent.TimeUnit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient(private val tokenStore: TokenStore) {
    private val gson = Gson()

    private val authInterceptor = Interceptor { chain ->
        val requestBuilder =
                chain.request()
                        .newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")

        val token = tokenStore.getAccessToken()
        if (!token.isNullOrBlank()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        chain.proceed(requestBuilder.build())
    }

    private val okHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .addInterceptor(
                            HttpLoggingInterceptor().apply {
                                level = HttpLoggingInterceptor.Level.BODY
                            }
                    )
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .build()

    private val retrofit =
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build()

    val service: BudgetikoApiService = retrofit.create(BudgetikoApiService::class.java)

    companion object {
        // For Android emulator + locally running Spring backend.
        // Replace with your deployed backend URL if needed.
        private const val BASE_URL = "http://10.0.2.2:8080/"
    }
}
