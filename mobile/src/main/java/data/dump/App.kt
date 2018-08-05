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
    private val CLIENT_ID: String = "0f50240f0ff642e29b57e31c9ae5a937"
    private val REDIRECT_URI:String = "data.dump"
    private val REQUEST_CODE = 1337

    private var player: Player? = null

    companion object {
        var instance: App? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        setContentView(R.layout.activity_principal)

        val builder: AuthenticationRequest.Builder = AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI)

        val scopes = listOf("teste", "teste").toTypedArray()

        builder.setScopes(scopes)

        var request: AuthenticationRequest = builder.build()

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request)

        ApiClient.request()
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
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                        var player: SpotifyPlayer? = spotifyPlayer
                        player?.addConnectionStateCallback(App.instance)
                        player?.addNotificationCallback(App.instance)
                    }

                })
            } else if (response.type == AuthenticationResponse.Type.ERROR) {
                Log.d(SPOTIFY_ERROR, response.error)
            }
        }

    }

    override fun onError(p0: Throwable?) {
        println(p0!!.message)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoggedOut() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoggedIn() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionMessage(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoginFailed(p0: Error?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTemporaryError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onPlaybackError(p0: Error?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPlaybackEvent(p0: PlayerEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onInitialized(p0: SpotifyPlayer?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
