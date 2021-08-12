package comtest.dandeliontest.todotest.notification

interface TaskManager {
    fun firstRunNotificationWorker()
    fun scheduleRunNotificationWorker()
    fun checkForUpdates()
}