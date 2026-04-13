package com.inmohub.frontend

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform