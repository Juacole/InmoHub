package com.inmohub.frontend.features.lead.data

import com.inmohub.frontend.core.network.NetworkClient
import com.inmohub.frontend.features.lead.requests.CreateLeadRequest
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

object LeadRepository {
    suspend fun createLead(request: CreateLeadRequest): Boolean {
        return try {
            val response = NetworkClient.client.post("${NetworkClient.BASE_URL}/leads/create") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            response.status.value in 200..299
        } catch (e: Exception) {
            println("Error al crear lead: ${e.message}")
            false
        }
    }
}