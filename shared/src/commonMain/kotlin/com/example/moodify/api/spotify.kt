package com.example.moodify.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import io.ktor.http.*
import kotlin.random.Random

/**
 * grid wise this is how the emotions would display on spotify's graph
 *
 *   angry | happy
 *  -------|------
 * relaxed | sad
 *
 * where the x-axis represents the valence and the y-axis represents the songs
 * energy.
 */
enum class EmotionalValues(
    val minValence: Double,
    val maxValence: Double,
    val minEnergy: Double,
    val maxEnergy: Double
) {
    happy(0.5, 1.0, 0.5, 1.0),
    sad(0.5, 1.0, 0.0, 0.5),
    angry(0.0, 0.5, 0.5, 1.0),
    relaxed(0.0, 0.5, 0.0, 0.5)
}

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

@Serializable
data class SpotifyUser(val id: String)

@Serializable
data class CreatePlaylistRequest(
    val name: String,
    val description: String = "",
    val public: Boolean = true
)

/**
 * This will only be used to create the playlist
 */
@Serializable
data class SpotifyPlaylist(
    val id: String,
    val name: String,
    val uri: String
)

/**
 * This is what will be used to determine what songs to add into the users
 * new playlist during the generation.
 */
@Serializable
data class SpotifySongSelection(
    val name: String,
    val album: String,
    val artist: String,
    val mood: EmotionalValues
)

class SpotifyPlaylistService(private val client: HttpClient) {

    // 1. You must fetch the user's ID to construct the playlist creation endpoint path
    suspend fun getCurrentUserId(accessToken: String): String {
        val user: SpotifyUser = client.get("https://spotify.com") {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
        }.body()
        return user.id
    }

    // 2. Create the playlist container under their account
    suspend fun createPlaylist(
        accessToken: String,
        userId: String,
        playlistName: String,
        isPublic: Boolean = true
    ): SpotifyPlaylist {
        return client.post("https://spotify.com") {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
            contentType(ContentType.Application.Json)
            setBody(CreatePlaylistRequest(name = playlistName, public = isPublic))
        }.body()
    }

    /**
     * This function will be used to get a list of songs from spotify based on
     * the mood that has been passed in.
     */
    suspend fun createSongList(mood: EmotionalValues) {
        // using the mood that was passed in make a request to the spotify api
        // that will make a list of songs based on the users preferred artists
        // and genre preference

        // call a function to add the songs iteratively because recursion would be
        // too much effort
    }

    /**
     * This function will be used to add the songs to the new playlist
     */
    suspend fun addToPlaylist(
        accessToken: String,
        userId: String,
        playlistName: String,
        songName: String
    ) {
        return client.post("https://spotify.com") {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
            contentType(ContentType.Application.Json)
            setBody(SpotifySongSelection(
                name = songName,
                album = "album",
                artist = "kanye",
                mood = EmotionalValues.sad
            ))
        }.body()
    }
}
