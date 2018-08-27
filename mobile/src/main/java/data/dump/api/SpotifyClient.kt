package data.dump.api

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.spotify.sdk.android.player.*
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.spotify.sdk.android.player.Config
import com.spotify.sdk.android.player.ConnectionStateCallback
import com.spotify.sdk.android.player.Player
import com.spotify.sdk.android.player.Spotify

class SpotifyClient : SpotifyPlayer.InitializationObserver, ConnectionStateCallback, Player.NotificationCallback {

    private val SPOTIFY_ERROR: String = "Spotify Player Error: "
    private val SPOTIFY_SUCCESS: String = "Spotify Player OK: "
    private val SPOTIFY_STATUS: String = "Spotify Player Status: "


    companion object {
        private val CLIENT_ID: String = "0f50240f0ff642e29b57e31c9ae5a937"
        private val REDIRECT_URI:String = "https://spoti-dump-data/callback/"
        private val REQUEST_AUTH_CODE = 1337

        private val SPOTIFY_ERROR: String = "Spotify Player Error: "

        var instance: SpotifyClient
        lateinit var player: Player

        init {
            instance = SpotifyClient()
            Log.d("NEW SPOTIFY CLIENT: ", "Criando novo cliente do Spotify!")
        }

        fun getUserToken(requestCode: Int, resultCode: Int, intent: Intent): String {
            var token: String = ""
            var saved: Boolean = false

            if (requestCode == REQUEST_AUTH_CODE) {
                val response: AuthenticationResponse = AuthenticationClient.getResponse(resultCode, intent)
                if (response.type == AuthenticationResponse.Type.TOKEN) {
                    token = response.accessToken
                } else if (response.type == AuthenticationResponse.Type.ERROR) {
                    Log.d(SPOTIFY_ERROR, response.error)
                } else if (response.type == AuthenticationResponse.Type.EMPTY) {
                    println(response.error)
                    println(response.state)
                }
            }
            return token
        }

        fun requestAuthenticationScreen(context: Activity) {
            val builder: AuthenticationRequest.Builder = AuthenticationRequest.Builder(CLIENT_ID,
                    AuthenticationResponse.Type.TOKEN,
                    REDIRECT_URI)
            val scopes: Array<String>  = arrayOf("user-read-private", "streaming")
            builder.setScopes(scopes)
            var request: AuthenticationRequest = builder.build()
            AuthenticationClient.openLoginActivity(context, REQUEST_AUTH_CODE, request)
        }
    }

    fun initializesPlayer(context: Context, token: String) {
        val playerConfig: Config = Config(context, token, CLIENT_ID)

        Spotify.getPlayer(playerConfig, this, object : SpotifyPlayer.InitializationObserver {
            override fun onError(problems: Throwable?) {
                if (problems != null) {
                    Log.d(SPOTIFY_ERROR, problems.message)
                }
            }

            override fun onInitialized(spotifyPlayer: SpotifyPlayer?) {
                player = spotifyPlayer!!
                player.addConnectionStateCallback(this@SpotifyClient)
                player.addNotificationCallback(this@SpotifyClient)
            }
        })
    }

    override fun onError(p0: Throwable?) {
        println(p0!!.message)
    }

    override fun onLoggedOut() {
        Log.d(SPOTIFY_STATUS, "User logged out")
    }

    override fun onLoggedIn() {
        Log.d(SPOTIFY_SUCCESS,"User logged in")

        player.playUri(null,"spotify:track:74uC8K6i7AL7mC7ohKCS7d", 0, 0)
    }

    override fun onConnectionMessage(p0: String?) {
    }

    override fun onLoginFailed(p0: Error?) {
    }

    override fun onTemporaryError() {
    }

    override fun onPlaybackError(p0: Error?) {
        Log.d(SPOTIFY_ERROR, p0!!.name)
    }

    override fun onPlaybackEvent(p0: PlayerEvent?) {
        Log.d(SPOTIFY_STATUS, p0!!.name)
    }

    override fun onInitialized(p0: SpotifyPlayer?) {
    }
}