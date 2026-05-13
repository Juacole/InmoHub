package com.inmohub.frontend.core.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object JwtUtils {

    @OptIn(ExperimentalEncodingApi::class)
    fun getUserRoleFromToken(token: String) : String? {
        try {
            val parts = token.split(".")
            if (parts.size != 3) return null

            val payload = String(Base64.UrlSafe.decode(parts[1]))
            val jsonObject = Json.decodeFromString<JsonObject>(payload)

            val rolesList = jsonObject["roles"]?.jsonArray
            return rolesList?.firstOrNull()?.jsonPrimitive?.content
        } catch (ex: Exception) {
            return null
        }
    }
}