package data.dump.api

import android.content.Context
import data.dump.App
import data.dump.util.ConnectionUtil

class ApiClient {

    companion object {
        private var context: Context? = null

        init {
            this.context = App.instance
        }

        fun request() {
            if (ConnectionUtil.isDataConnectionAvailable(context!!)) {
                println("Conexão top")
            }
        }
    }
}