package com.inmohub.frontend.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val phone: String,
    val token: String? = null,
    val status: String
)

data class UserSession(
    val id: String,
    val username: String,
    val role: String,
    val token: String
)