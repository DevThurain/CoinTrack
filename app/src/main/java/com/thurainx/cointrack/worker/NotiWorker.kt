package com.thurainx.cointrack.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.thurainx.cointrack.R
import com.thurainx.cointrack.common.WorkerKeys
import kotlinx.coroutines.delay
import java.lang.Exception
import kotlin.random.Random
import kotlin.time.Duration

class NotiWorker(
    private val context: Context,
    private val params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        try {
            buildNotificationChannel()
        } catch (e: Exception) {
            return Result.failure(workDataOf(
                WorkerKeys.ERROR_KEY to "Unable to build notification"
            ))
        }
        delay(5000)
        delay(5000)
        return Result.success()
    }

    private suspend fun buildNotificationChannel() {
        setForeground(
            foregroundInfo = ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, "noti_channel")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Noti Title")
                    .setContentText("working in worker")
                    .build()
            )
        )

    }
}