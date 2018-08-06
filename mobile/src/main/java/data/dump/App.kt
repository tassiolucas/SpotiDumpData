package data.dump

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import data.dump.api.ApiClient
import android.content.Intent
import android.util.Log
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.spotify.sdk.android.player.*

class App : AppCompatActivity(), SpotifyPlayer.InitializationObserver, ConnectionStateCallback, Player.NotificationCallback {

    private val SPOTIFY_ERROR: String = "Spotify Player Error: "
    private val SPOTIFY_SUCCESS: String = "Spotify Player OK: "
    private val SPOTIFY_STATUS: String = "Spotify Player Status: "
    private val CLIENT_ID: String = "0f50240f0ff642e29b57e31c9ae5a937"
    private val REDIRECT_URI:String = "spotidumpdata.com://callback"
    private val REQUEST_CODE = 1337

    companion object {
        lateinit var instance: App
        lateinit var player: Player
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        setContentView(R.layout.activity_principal)

        val builder: AuthenticationRequest.Builder = AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI)

        val scopes = listOf("user-read-private", "streaming").toTypedArray()

        builder.setScopes(scopes)

        var request: AuthenticationRequest = builder.build()

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request)

        ApiClient.request()
    }

    override fun onDestroy() {
        super.onDestroy()
        Spotify.destroyPlayer(this);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == REQUEST_CODE) {
            val response: AuthenticationResponse = AuthenticationClient.getResponse(resultCode, intent)
            if (response.type == AuthenticationResponse.Type.TOKEN) {
                val playerConfig: Config = Config(this, response.accessToken, CLIENT_ID)

                Spotify.getPlayer(playerConfig, this, object: SpotifyPlayer.InitializationObserver {
                    override fun onError(problems: Throwable?) {
                        if (problems != null) {
                            Log.d(SPOTIFY_ERROR, problems.message)
                        }
                    }

                    override fun onInitialized(spotifyPlayer: SpotifyPlayer?) {
                        player = spotifyPlayer!!
                        player.addConnectionStateCallback(App.instance)
                        player.addNotificationCallback(App.instance)
                    }

                })
            } else if (response.type == AuthenticationResponse.Type.ERROR) {
                Log.d(SPOTIFY_ERROR, response.error)
            }
        }

    }

    override fun onError(p0: Throwable?) {
        println(p0!!.message)

    }

    override fun onLoggedOut() {
        Log.d(SPOTIFY_STATUS, "User logged out");
    }

    override fun onLoggedIn() {
        Log.d(SPOTIFY_SUCCESS,"User logged in")

        player.playUri(null,"spotify:track:3Ipgo2twyvBySMwsTzunlH", 0, 0)
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
