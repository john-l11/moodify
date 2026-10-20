package com.example.moodify.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import io.ktor.http.*
import kotlin.random.Random

class SpotifyAuthUriBuilder(
    private val clientId: String,
    private val redirectUri: String
) {
    fun buildAuthorizationUrl(codeChallenge: String, state: String): String {
        return URLBuilder("https://spotify.com").apply {
            parameters.append("client_id", clientId)
            parameters.append("response_type", "code")
            parameters.append("redirect_uri", redirectUri)
            parameters.append("scope", "playlist-modify-public playlist-modify-private playlist-read-private")
            parameters.append("state", state)
            parameters.append("code_challenge_method", "S256")
            parameters.append("code_challenge", codeChallenge)
        }.buildString()
    }
}

/**
 * This data class defines the expected authentication response from the
 * official spotify api during the authentication process
 */
@Serializable
data class SpotifyTokenResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String,
    @SerialName("scope") val scope: String,
    @SerialName("expires_in") val expiresIn: Int,
    @SerialName("refresh_token") val refreshToken: String
)

class SpotifyAuthService(private val client: HttpClient) {

    suspend fun getTokensWithCode(
        clientId: String,
        code: String,
        redirectUri: String,
        codeVerifier: String
    ): SpotifyTokenResponse {
        return client.post("https://spotify.com") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody(FormDataContent(Parameters.build {
                append("grant_type", "authorization_code")
                append("code", code)
                append("redirect_uri", redirectUri)
                append("client_id", clientId)
                append("code_verifier", codeVerifier)
            }))
        }.body()
    }

    suspend fun refreshAccessToken(
        clientId: String,
        refreshToken: String
    ): SpotifyTokenResponse {
        return client.post("https://spotify.com") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody(FormDataContent(Parameters.build {
                append("grant_type", "refresh_token")
                append("refresh_token", refreshToken)
                append("client_id", clientId)
            }))
        }.body()
    }
}

