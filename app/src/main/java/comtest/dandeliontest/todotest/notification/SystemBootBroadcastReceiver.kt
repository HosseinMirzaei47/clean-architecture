package comtest.dandeliontest.todotest.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SystemBootBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var taskManager: TaskManager

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            taskManager.firstRunNotificationWorker()
        }
    }
}

