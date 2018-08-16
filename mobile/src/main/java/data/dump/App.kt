package data.dump

import android.app.Application
import android.content.Context
import data.dump.api.SpotifyClient
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {

    companion object {
        lateinit var instance: App
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext

        Realm.init(this)
        val config: RealmConfiguration = RealmConfiguration.Builder()
                .name("SpotiDumpDataBase.realm")
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)
    }

}
