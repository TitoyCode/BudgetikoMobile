package com.budgetiko.budgetikomobile.data.network

import android.content.Context
import com.budgetiko.budgetikomobile.data.network.model.ApiEnvelope
import com.budgetiko.budgetikomobile.data.network.model.AuthData
import com.budgetiko.budgetikomobile.data.network.model.ChangePasswordRequest
import com.budgetiko.budgetikomobile.data.network.model.LoginRequest
import com.budgetiko.budgetikomobile.data.network.model.MessageData
import com.budgetiko.budgetikomobile.data.network.model.ProfileData
import com.budgetiko.budgetikomobile.data.network.model.RegisterRequest
import com.budgetiko.budgetikomobile.data.network.model.UpdateProfileRequest
import com.google.gson.Gson
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class BudgetikoRepository private constructor(context: Context) {
    private val tokenStore = TokenStore(context.applicationContext)
    private val apiClient = ApiClient(tokenStore)
    private val gson = Gson()

    suspend fun register(
            fullName: String,
            email: String,
            password: String,
            confirmPassword: String
    ): ApiResult<AuthData> {
        return safeApiCall {
            apiClient.service.register(
                    RegisterRequest(
                            fullName = fullName,
                            email = email,
                            password = password,
                            confirmPassword = confirmPassword
                    )
            )
        }
    }

    suspend fun login(email: String, password: String): ApiResult<AuthData> {
        val result = safeApiCall {
            apiClient.service.login(LoginRequest(email = email, password = password))
        }

        if (result is ApiResult.Success) {
            val token = result.data.accessToken ?: result.data.token
            if (!token.isNullOrBlank()) {
                tokenStore.saveAccessToken(token)
            }
        }

        return result
    }

    suspend fun getProfile(): ApiResult<ProfileData> = safeApiCall {
        apiClient.service.getProfile()
    }

    suspend fun updateProfile(fullName: String, email: String): ApiResult<ProfileData> {
        return safeApiCall {
            apiClient.service.updateProfile(
                    UpdateProfileRequest(fullName = fullName, email = email)
            )
        }
    }

    suspend fun changePassword(
            currentPassword: String,
            newPassword: String,
            confirmPassword: String
    ): ApiResult<MessageData> {
        return safeApiCall {
            apiClient.service.changePassword(
                    ChangePasswordRequest(
                            currentPassword = currentPassword,
                            newPassword = newPassword,
                            confirmPassword = confirmPassword
                    )
            )
        }
    }

    fun hasSession(): Boolean = tokenStore.hasToken()

    fun logout() {
        tokenStore.clear()
    }

    private suspend fun <T> safeApiCall(
            call: suspend () -> Response<ApiEnvelope<T>>
    ): ApiResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = call()
                val body = response.body()

                if (response.isSuccessful && body != null && body.success) {
                    val payload = body.data
                    if (payload != null) {
                        ApiResult.Success(payload)
                    } else {
                        ApiResult.Error("Empty response from server", response.code())
                    }
                } else {
                    ApiResult.Error(
                            message =
                                    resolveHttpError(
                                            response.code(),
                                            body?.error?.message,
                                            response.errorBody()?.string()
                                    ),
                            statusCode = response.code()
                    )
                }
            } catch (ex: IOException) {
                ApiResult.Error("No internet connection. Please check your network and try again.")
            } catch (ex: Exception) {
                ApiResult.Error(ex.message ?: "Unexpected error occurred")
            }
        }
    }

    private fun resolveHttpError(
            statusCode: Int,
            bodyMessage: String?,
            rawErrorBody: String?
    ): String {
        val parsedBodyMessage = parseErrorFromRawBody(rawErrorBody)
        val message = bodyMessage ?: parsedBodyMessage

        return when (statusCode) {
            400 -> message ?: "Invalid request. Please check your input."
            401 -> message ?: "Unauthorized (401). Please login again."
            500 -> "Server error (500). Please try again later."
            else -> message ?: "Request failed with status code $statusCode"
        }
    }

    private fun parseErrorFromRawBody(rawErrorBody: String?): String? {
        if (rawErrorBody.isNullOrBlank()) return null

        return try {
            val envelope = gson.fromJson(rawErrorBody, ApiEnvelope::class.java)
            (envelope.error as? Map<*, *>)?.get("message")?.toString()
        } catch (_: Exception) {
            rawErrorBody
        }
    }

    companion object {
        @Volatile private var instance: BudgetikoRepository? = null

        fun getInstance(context: Context): BudgetikoRepository {
            return instance
                    ?: synchronized(this) {
                        instance ?: BudgetikoRepository(context).also { instance = it }
                    }
        }
    }
}
