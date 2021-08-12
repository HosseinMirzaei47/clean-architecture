package com.speaking.partner.notification

interface TaskManager {
    fun firstRunNotificationWorker()
    fun scheduleRunNotificationWorker()
    fun checkForUpdates()
}