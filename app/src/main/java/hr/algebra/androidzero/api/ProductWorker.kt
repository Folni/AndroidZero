package hr.algebra.androidzero.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import hr.algebra.androidzero.api.ProductFetcher
import hr.algebra.androidzero.model.Item

class ProductWorker(private val context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    override fun doWork(): Result {
        try {
            ProductFetcher(context).fetchItems()
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}