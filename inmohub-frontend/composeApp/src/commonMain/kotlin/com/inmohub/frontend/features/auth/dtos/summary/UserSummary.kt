package com.inmohub.frontend.features.auth.dtos.summary

import kotlinx.serialization.Serializable

@Serializable
data class UserSummary(
    val id: String,
    val username: String,
    val email: String,
    val role: String,
    val phone: String
)