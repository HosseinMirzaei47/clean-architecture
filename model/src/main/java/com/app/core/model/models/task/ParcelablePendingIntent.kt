package com.app.core.model.models.task

import android.app.PendingIntent
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelablePendingIntent(
    val pendingIntent: PendingIntent
) : Parcelable