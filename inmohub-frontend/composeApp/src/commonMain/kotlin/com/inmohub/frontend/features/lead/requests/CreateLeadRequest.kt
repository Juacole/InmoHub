package com.inmohub.frontend.features.lead.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateLeadRequest(
    val name: String,
    val email: String,
    val phone: String? = null,
    val message: String? = null,
    val source: String,
    val propertyId: String
)