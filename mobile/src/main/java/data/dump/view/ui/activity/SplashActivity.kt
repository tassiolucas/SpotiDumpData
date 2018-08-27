package data.dump.view.ui.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import butterknife.ButterKnife
import data.dump.R
import data.dump.api.SpotifyClient
import data.dump.databinding.SplashDataBinding
import data.dump.model.DAO.UserDAO
import data.dump.view.ui.activity.base.BaseActivity
import data.dump.view_model.SplashViewModel

class SplashActivity: BaseActivity() {

    private lateinit var binding: SplashDataBinding
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        ButterKnife.bind(this)

        viewModel = SplashViewModel(this)

        initScreen()
    }

    fun initScreen() {
        if (UserDAO.isLogged()) {

        } else {
            Toast.makeText(this, "Você precisa logar antes iniciar.", Toast.LENGTH_SHORT)
            SpotifyClient.requestAuthenticationScreen(this)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        println("Nova intent!")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var token:String = SpotifyClient.getUserToken(requestCode, resultCode, intent)

        if (token != "") {
            UserDAO.saveToken(token)
        } else {
            Toast.makeText(this, "Erro no login do Spotify, tente novamente mais tarde", Toast.LENGTH_SHORT)
        }

    }
}