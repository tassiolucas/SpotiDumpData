package data.dump

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.spotify.sdk.android.player.*
import data.dump.api.SpotifyClient

class App : AppCompatActivity() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        setContentView(R.layout.activity_principal)

        SpotifyClient.intance.requestAuthentication(this)

        // TODO: Criar estrutura Realm para salvar token
        //SpotifyClient.intance.saveUserToken()
    }

    override fun onDestroy() {
        super.onDestroy()
        Spotify.destroyPlayer(this);
    }
}
