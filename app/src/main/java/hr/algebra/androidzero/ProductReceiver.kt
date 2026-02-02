package hr.algebra.androidzero

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import hr.algebra.androidzero.framework.setBooleanPreference
import hr.algebra.androidzero.framework.startActivity

class ProductReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.setBooleanPreference(DATA_IMPORTED)
        context.startActivity<HostActivity>()    }
}