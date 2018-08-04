package data.dump

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import data.dump.api.ApiClient

class App : AppCompatActivity() {

    companion object {
        var instance: App? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        setContentView(R.layout.activity_principal)

        ApiClient.request()
    }
}
