package hr.algebra.androidzero.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import hr.algebra.androidzero.api.ProductFetcher
import hr.algebra.androidzero.model.Item

class ProductWorker(private val context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    override fun doWork(): Result {
        // WorkManager poziva fetchItems() iz tvog Fetchera
        // Budući da smo unutar doWork() metode, ovo se izvodi u pozadinskoj dretvi
        try {
            ProductFetcher(context).fetchItems()
            return Result.success()
        } catch (e: Exception) {
            // Ako dođe do greške (npr. nema interneta), javi WorkManageru da nije uspjelo
            return Result.failure()
        }
    }
}