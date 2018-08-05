package data.dump.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectionUtil {
    companion object {
        fun isDataConnectionAvailable(context: Context): Boolean {
            val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo: NetworkInfo = connectivityManager.activeNetworkInfo

            return activeNetworkInfo.isConnected
        }
    }
}