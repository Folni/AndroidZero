package hr.algebra.androidzero

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import hr.algebra.androidzero.api.ProductWorker
import hr.algebra.androidzero.databinding.ActivitySplashScreenBinding
import hr.algebra.androidzero.framework.applyAnimation
import hr.algebra.androidzero.framework.callDelayed
import hr.algebra.androidzero.framework.getBooleanPreference
import hr.algebra.androidzero.framework.isOnline
import hr.algebra.androidzero.framework.startActivity

private const val DELAY = 3000L
const val DATA_IMPORTED = "hr.algebra.androidzero.data_imported"
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
        redirect()

    }

    private fun startAnimations() {

        binding.tvSplash.applyAnimation(R.anim.blink)
        binding.ivSplash.applyAnimation(R.anim.rotate)

    }

    private fun redirect() {

        if(getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY) { startActivity<HostActivity>() }
        } else {
            if(isOnline()) {
                WorkManager.getInstance(this).apply {
                    enqueueUniqueWork(
                        DATA_IMPORTED,
                        ExistingWorkPolicy.KEEP,
                        OneTimeWorkRequest.from(ProductWorker::class.java)
                    )
                }
            } else {
                binding.tvSplash.text = getString(R.string.no_internet)
                callDelayed(DELAY) { finish() }
            }
        }
    }
}

