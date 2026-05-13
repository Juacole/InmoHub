package com.inmohub.frontend.features.auth.data

import com.inmohub.frontend.core.network.NetworkClient
import com.inmohub.frontend.features.auth.dtos.AuthResponseDto
import com.inmohub.frontend.features.auth.dtos.LoginRequest
import com.inmohub.frontend.features.auth.dtos.RegisterRequest
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

object AuthRepository {
    private val sessionManager = NetworkClient.sessionManager

    suspend fun register(request: RegisterRequest): Boolean {
        return try {
            val response = NetworkClient.client.post("${NetworkClient.BASE_URL}/users/register") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            response.status.value in 200..299
        } catch (e: Exception) {
            println("Error registrando usuario: ${e.message}")
            false
        }
    }

    suspend fun login(email: String, password: String): AuthResponseDto? {
        return try {
            val response = NetworkClient.client.post("${NetworkClient.BASE_URL}/users/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(email, password))
            }
            if (response.status.value == 200) {
                val tokens = response.body<AuthResponseDto>()
                sessionManager.saveTokens(tokens.accessToken, tokens.refreshToken)
                tokens
            } else {
                null
            }
        } catch (e: Exception) {
            println("ERROR CRÍTICO EN LOGIN:")
            e.printStackTrace()
            println("Error Login: ${e.message}")
            null
        }
    }
}