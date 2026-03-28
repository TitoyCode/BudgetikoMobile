package com.budgetiko.budgetikomobile.data.network.model

import com.google.gson.annotations.SerializedName

data class ApiEnvelope<T>(
        @SerializedName("success") val success: Boolean = false,
        @SerializedName("data") val data: T? = null,
        @SerializedName("error") val error: ApiErrorBody? = null,
        @SerializedName("timestamp") val timestamp: String? = null
)

data class ApiErrorBody(@SerializedName("message") val message: String? = null)

data class LoginRequest(
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String
)

data class RegisterRequest(
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String,
        @SerializedName("confirmPassword") val confirmPassword: String,
        @SerializedName("fullName") val fullName: String
)

data class UpdateProfileRequest(
        @SerializedName("fullName") val fullName: String,
        @SerializedName("email") val email: String,
        @SerializedName("username") val username: String? = null,
        @SerializedName("address") val address: String? = null
)

data class ChangePasswordRequest(
        @SerializedName("currentPassword") val currentPassword: String,
        @SerializedName("confirmPassword") val confirmPassword: String,
        @SerializedName("newPassword") val newPassword: String
)

data class AuthData(
        @SerializedName("access_token") val accessToken: String? = null,
        @SerializedName("refresh_token") val refreshToken: String? = null,
        @SerializedName("token") val token: String? = null,
        @SerializedName("message") val message: String? = null
)

data class ProfileData(
        @SerializedName("user") val user: ProfileUser? = null,
        @SerializedName("message") val message: String? = null
)

data class ProfileUser(
        @SerializedName("id") val id: String = "",
        @SerializedName("fullName") val fullName: String = "",
        @SerializedName("email") val email: String = "",
        @SerializedName("username") val username: String = "",
        @SerializedName("avatarUrl") val avatarUrl: String? = null
)

data class MessageData(@SerializedName("message") val message: String? = null)
