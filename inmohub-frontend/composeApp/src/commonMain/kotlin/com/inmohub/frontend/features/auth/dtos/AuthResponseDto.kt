package com.inmohub.frontend.features.auth.dtos

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDto(
    val accessToken: String,
    val refreshToken: String
) {}
