package com.budgetiko.budgetikomobile.data.network

import com.budgetiko.budgetikomobile.data.network.model.ApiEnvelope
import com.budgetiko.budgetikomobile.data.network.model.AuthData
import com.budgetiko.budgetikomobile.data.network.model.ChangePasswordRequest
import com.budgetiko.budgetikomobile.data.network.model.LoginRequest
import com.budgetiko.budgetikomobile.data.network.model.MessageData
import com.budgetiko.budgetikomobile.data.network.model.ProfileData
import com.budgetiko.budgetikomobile.data.network.model.RegisterRequest
import com.budgetiko.budgetikomobile.data.network.model.UpdateProfileRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface BudgetikoApiService {
    @POST("api/v1/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<ApiEnvelope<AuthData>>

    @POST("api/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<ApiEnvelope<AuthData>>

    @GET("api/v1/profile") suspend fun getProfile(): Response<ApiEnvelope<ProfileData>>

    @PUT("api/v1/profile")
    suspend fun updateProfile(
            @Body request: UpdateProfileRequest
    ): Response<ApiEnvelope<ProfileData>>

    @PUT("api/v1/profile/password")
    suspend fun changePassword(
            @Body request: ChangePasswordRequest
    ): Response<ApiEnvelope<MessageData>>
}
