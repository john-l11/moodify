package com.example.moodify

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform